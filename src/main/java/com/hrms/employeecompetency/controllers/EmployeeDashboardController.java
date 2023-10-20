package com.hrms.employeecompetency.controllers;

import com.hrms.employeecompetency.dto.*;
import com.hrms.employeecompetency.graphql.EmployeePerformancePagination;
import com.hrms.employeecompetency.graphql.EmployeeRatingPagination;
import com.hrms.employeecompetency.mapper.EmployeeMapperService;
import com.hrms.employeecompetency.services.*;
import com.hrms.employeecompetency.specifications.CompetencyEvaluationSpecifications;
import com.hrms.employeemanagement.models.Employee;
import com.hrms.employeemanagement.models.SkillSet;
import com.hrms.employeecompetency.services.CompetencyCycleService;
import com.hrms.employeecompetency.services.CompetencyEvaluationService;
import com.hrms.employeecompetency.services.EmployeeCareerPathService;
import com.hrms.employeecompetency.specifications.CompetencyEvaluationSpec;
import com.hrms.employeemanagement.models.Employee;
import com.hrms.employeemanagement.models.JobLevel;
import com.hrms.employeemanagement.paging.Pagination;
import com.hrms.employeemanagement.services.JobLevelService;
import com.hrms.employeemanagement.specifications.EmployeeSpec;
import com.hrms.employeemanagement.specifications.EmployeeSpecifications;
import com.hrms.global.Range;
import com.hrms.performancemanagement.model.EmployeePerformance;
import com.hrms.employeemanagement.services.EmployeeService;
import com.hrms.performancemanagement.services.PerformanceService;
import com.unboundid.util.Nullable;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@Slf4j
public class EmployeeDashboardController {
    @Autowired
    EmployeeService employeeService;

    @Autowired
    PerformanceService performanceService;

    @Autowired
    CompetencyService competencyService;

    @Autowired
    CompetencyCycleService competencyCycleService;

    @Autowired
    EmployeeMapperService employeeMapperService;

    @Autowired
    JobLevelService jobLevelService;
    @Autowired
    CompetencyEvaluationService competencyEvaluationService;

    @Autowired
    EmployeeCareerPathService employeeCareerPathService;

    @Autowired
    PerformanceRangeService performanceRangeService;

    @Autowired
    SkillSetEvaluationService skillSetEvaluationService;

    HashMap<String, Range> performanceCategories;

    @QueryMapping
    public EmployeeOverviewDto employeeOverview(@Argument Integer employeeId) {
        var skillSetOfEmployee = skillSetEvaluationService.findAllByEmployeeId(employeeId)
                .stream().map(skillSetEvaluation -> skillSetEvaluation.getPositionSkillSet().getSkillSet())
                .distinct().collect(Collectors.toList());
        var employeeOverviewDto = new EmployeeOverviewDto();
        employeeOverviewDto.setSkills(skillSetOfEmployee);
        employeeOverviewDto.setEmployee(employeeService.findById(employeeId));
        employeeOverviewDto.setInterests(skillSetOfEmployee);
        employeeOverviewDto.setCertification(List.of("Bachelor of Science in Computer Science", "Master of Science in Computer Science"));
        return employeeOverviewDto;
    }

//    @QueryMapping
//    public CareerPathSummaryDto careerPathSummary(@Argument Integer id) {
//        //return employeeMapperService.careerPathSummary(employeeService.findById(id));
//    }

    @QueryMapping
    public List<EmployeePerformance> topEmployeePerformance(@Argument Integer performanceCycleId,@Nullable @Argument Integer limit) {
        return performanceService.findByPerformanceCycleId(performanceCycleId, limit);
    }

    @QueryMapping
    public EmployeePerformancePagination employeesPerformance(@Argument Integer pageNo, @Argument Integer pageSize) {
        var pageable = PageRequest.of(pageNo - 1, pageSize, Sort.by(Sort.Direction.DESC, "finalAssessment"));
        var employeesPerformance = performanceService.findAll(pageable);
        return new EmployeePerformancePagination(employeesPerformance, new Pagination(pageNo, pageSize, employeesPerformance.getTotalElements(), employeesPerformance.getTotalPages()));
    }

    @QueryMapping
    public List<EmployeeRating> topEmployeeCompetencies(@Argument int competencyCycleId, @Argument int limit) {
        return competencyEvaluationService.findByCompetencyCycleId(competencyCycleId, limit);
    }

    @QueryMapping
    public EmployeeRatingPagination employeesCompetency(@Argument Integer pageNo, @Argument Integer pageSize) {
        List<EmployeeRating> employeeRatings = new ArrayList<>();
        for (Employee employee : employeeService.findAll()) {
            var filterLatestCompetencyCycle = CompetencyEvaluationSpec.filterLatestCompetencyCycle(competencyCycleService.getLatestCompetencyCycle().getId());
            var filterEqualEmployee = CompetencyEvaluationSpec.hasEmployeeId(employee.getId());
            var employeeEvaluation = competencyEvaluationService.findAll(filterLatestCompetencyCycle.and(filterEqualEmployee));
            var score = employeeEvaluation.stream().reduce(0, (subtotal, element) -> subtotal + element.getProficiencyLevel().getScore(), Integer::sum);
            if (!employeeEvaluation.isEmpty())
                employeeRatings.add(new EmployeeRating(employee, (float) (score/employeeEvaluation.size())));
        }

        var pageable = PageRequest.of(pageNo - 1, pageSize, Sort.by(Sort.Direction.DESC, "rating"));
        var resultPage = new PageImpl<>(employeeRatings, pageable, employeeRatings.size());
        return new EmployeeRatingPagination(resultPage, new Pagination(pageNo, pageSize, resultPage.getTotalElements(), resultPage.getTotalPages()));
    }

    @QueryMapping
    public List<EmployeePotentialPerformance> employeesPotentialPerformance(@Argument int departmentId) {
        var result = new ArrayList<EmployeePotentialPerformance>();
        for (Employee employee : employeeService.findAll(EmployeeSpec.getByDepartment(departmentId))) {
            //Get latest performance cycle which this employee was evaluated
            var latestPerformanceCycleOfThisEmployee = performanceService.findLatestPerformanceCycleOfEmployee(employee.getId());
            if (latestPerformanceCycleOfThisEmployee == null) {
                continue;
            }
            var performanceScore = performanceService
                    .findByEmployeeIdAndPerformanceCyclePerformanceCycleId(
                            employee.getId(),
                            latestPerformanceCycleOfThisEmployee.getPerformanceCycleId()
                    )
                    .getFinalAssessment().intValue();

            var potentialScore = performanceService.findByEmployeeIdAndPerformanceCyclePerformanceCycleId(
                        employee.getId(),
                        latestPerformanceCycleOfThisEmployee.getPerformanceCycleId()
                    ).getPotentialScore().intValue();
            result.add(new EmployeePotentialPerformance(
                    employee,
                    "https://cdn1.iconfinder.com/data/icons/user-pictures/101/malecostume-512.png",
                    potentialScore,
                    performanceScore
            ));
        }
        return result;
    }

    @QueryMapping
    public PerformanceByJobLevelChart performanceByJobLevel(
            @Argument Integer performanceCycleId,
            @Argument Integer positionId)
    {
        var result = new ArrayList<PerformanceByJobLevel>();
        var employeesHasPosition = employeeService.findAll(EmployeeSpec.hasPositionId(positionId));
        for (var jobLevel: jobLevelService.findAll()) {
            var employeesHasJobLevel = employeesHasPosition
                    .stream()
                    .filter(employee -> employee.getPosition().getId() == jobLevel.getId())
                    .toList();
        var datasets = new ArrayList<List<Float>>();

        List<EmployeePerformance> filteredEmployees = performanceService.findAllByPositionIdAndPerformanceCycleId(
                positionId,
                performanceCycleId
        );
        log.info("Filtered employees: " + filteredEmployees.size());

        for (var performanceRange : performanceRangeService.getAllPerformanceRange()) {
            var dataset = new ArrayList<Float>();
            var filteredRangeEmployees = filteredEmployees.stream().filter(employeePerformance
                    -> employeePerformance.getFinalAssessment() >= performanceRange.getMinValue() &&
                    employeePerformance.getFinalAssessment() <= performanceRange.getMaxValue()).toList();
            log.info("----------------------------------------------Filtered category employees: " + filteredRangeEmployees.size());
            if (filteredRangeEmployees.isEmpty()) {
                datasets.add(dataset);
                continue;
            }
            jobLevelService.findAll().forEach(
                    jobLevel -> {
                        var filteredCategoryJobLevelEmployees = filteredRangeEmployees.stream()
                                .filter(employeePerformance -> employeePerformance.getEmployee().getJobLevel() == jobLevel)
                                .toList();
                        log.info("++++ Filtered category job level employees: " + filteredCategoryJobLevelEmployees.size());
                        float percentage = ((float) filteredCategoryJobLevelEmployees.size() / filteredRangeEmployees.size()) * 100;
                        dataset.add(percentage);
                    }
            );
            datasets.add(dataset);
        }

        datasets.forEach(
                dataset -> dataset.forEach(
                        data -> log.info("Data: " + data)
                )
        );

        return null;
    }

    @QueryMapping
    public CareerPathSummaryDto careerPathSummary(@Argument Integer employeeId) {
        var chosenPostion = employeeCareerPathService.findAllByEmployeeId(employeeId).stream().map(
                employeeCareerPath -> employeeCareerPath.getPositionLevel().getPosition()
        ).toList();


        ///chosenPostion.sort((o1, o2) -> o1.getLevel() - o2.getLevel());
        return null;
    }

    public void getAtGlance(@Argument Integer employeeId) {
        employeeService.findById(employeeId);
    }

    public void getOverallCompetencyScore(@Argument Integer employeeId) {

    }

    public void getTopSkills(@Argument Integer employeeId) {

    }

    public void getTopInterest(@Argument Integer employeeId) {

    }

    public void getImprovements(@Argument Integer employeeId) {

    }

    public void getPerformanceRatingScore(@Argument Integer employeeId) {

    }
}
