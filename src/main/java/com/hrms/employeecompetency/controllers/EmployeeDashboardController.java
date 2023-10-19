package com.hrms.employeecompetency.controllers;

import com.hrms.employeecompetency.dto.*;
import com.hrms.employeecompetency.graphql.EmployeePerformancePagination;
import com.hrms.employeecompetency.graphql.EmployeeRatingPagination;
import com.hrms.employeecompetency.mapper.EmployeeMapperService;
import com.hrms.employeecompetency.models.CompetencyEvaluation;
import com.hrms.employeecompetency.services.CompetencyCycleService;
import com.hrms.employeecompetency.services.CompetencyEvaluationService;
import com.hrms.employeecompetency.services.EmployeeCareerPathService;
import com.hrms.employeecompetency.specifications.CompetencyEvaluationSpecifications;
import com.hrms.employeemanagement.models.Employee;
import com.hrms.employeemanagement.models.JobLevel;
import com.hrms.employeemanagement.models.PositionLevel;
import com.hrms.employeemanagement.paging.Pagination;
import com.hrms.employeemanagement.repositories.JobLevelRepository;
import com.hrms.employeemanagement.services.JobLevelService;
import com.hrms.employeemanagement.specifications.EmployeeSpecifications;
import com.hrms.performancemanagement.model.EmployeePerformance;
import com.hrms.employeecompetency.services.CompetencyService;
import com.hrms.performancemanagement.service.PerformanceService;
import com.hrms.employeemanagement.services.EmployeeService;
import com.unboundid.util.Nullable;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.List;

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

    @QueryMapping
    public EmployeeOverviewDto employeeOverview(@Argument Integer id) {
        return employeeMapperService.employeeOverview(employeeService.findById(id));
    }

//    @QueryMapping
//    public CareerPathSummaryDto careerPathSummary(@Argument Integer id) {
//        return employeeMapperService.careerPathSummary(employeeService.findById(id));
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
            var filterLatestCompetencyCycle = CompetencyEvaluationSpecifications.filterLatestCompetencyCycle(competencyCycleService.getLatestCompetencyCycle().getId());
            var filterEqualEmployee = CompetencyEvaluationSpecifications.hasEmployeeId(employee.getId());
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
        for (Employee employee : employeeService.findAll(EmployeeSpecifications.hasDepartmentId(departmentId))) {
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
    public List<PerformanceByJobLevel> performanceByJobLevel(
            @Argument Integer performanceCycleId,
            @Argument Integer positionId)
    {
                var result = new ArrayList<PerformanceByJobLevel>();
        Specification<Employee> filterByPosition = Specification.where((root, query, cb) -> cb.equal(root.get("positionLevel").get("position").get("id"), positionId));
        var employees = employeeService.findAll(filterByPosition);
        for (JobLevel jobLevel: jobLevelService.findAll()) {
            var employeesWithJobLevel = employees.stream().filter(employee -> employee.getPositionLevel().getJobLevel().getId() == jobLevel.getId()).toList();
            var totalEmployees = employeesWithJobLevel.size();
            if (totalEmployees == 0)  {
                continue;
            }
            float unsatis =  performanceService.countByRatingRangeAndPerformanceCycleId(0, 1, 1) / totalEmployees;
            float pme =  performanceService.countByRatingRangeAndPerformanceCycleId(2, 2, 1) / totalEmployees;
            float meetExpectation =  performanceService.countByRatingRangeAndPerformanceCycleId(2, 3, 1) / totalEmployees;
            float exceedExpectation =  performanceService.countByRatingRangeAndPerformanceCycleId(3, 4, 1) / totalEmployees;
            float outstanding =  performanceService.countByRatingRangeAndPerformanceCycleId(4, 5, 1) / totalEmployees;
            float early = 1 - (unsatis + pme + meetExpectation + exceedExpectation + outstanding);
            result.add(new PerformanceByJobLevel(jobLevel, early, unsatis, pme, meetExpectation, exceedExpectation, outstanding));
        }
        return result;
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
