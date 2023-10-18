package com.hrms.employeecompetency.controllers;

import com.hrms.employeecompetency.dto.EmployeeOverviewDto;
import com.hrms.employeecompetency.dto.EmployeePotentialPerformance;
import com.hrms.employeecompetency.dto.EmployeeRating;
import com.hrms.employeecompetency.graphql.EmployeePerformancePagination;
import com.hrms.employeecompetency.graphql.EmployeeRatingPagination;
import com.hrms.employeecompetency.mapper.EmployeeMapperService;
import com.hrms.employeecompetency.models.CompetencyEvaluation;
import com.hrms.employeecompetency.services.CompetencyCycleService;
import com.hrms.employeecompetency.services.CompetencyEvaluationService;
import com.hrms.employeemanagement.models.Employee;
import com.hrms.employeemanagement.paging.Pagination;
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
    CompetencyEvaluationService competencyEvaluationService;

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
            Specification<CompetencyEvaluation> filterLatestCompetencyCycle = Specification.where((root, query, cb) -> cb.equal(root.get("competencyCycle").get("id"), competencyCycleService.getLatestCompetencyCycle().getId()));
            Specification<CompetencyEvaluation> filterEqualEmployee = Specification.where((root, query, cb) -> cb.equal(root.get("employee").get("id"), employee.getId()));
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
        Specification<Employee> filterByDepartment = Specification.where((root, query, cb) -> cb.equal(root.get("department").get("id"), departmentId));
        for (Employee employee : employeeService.findAll(filterByDepartment)) {
            //Get latest performance cycle which this employee was evaluated
            var latestPerformanceCycleOfThisEmployee = performanceService.findLatestPerformanceCycleOfEmployee(employee.getId());
            var performanceScore = performanceService.findByEmployeeIdAAndPerformanceCyclePerformanceCycleId(
                    employee.getId(),
                    latestPerformanceCycleOfThisEmployee.getPerformanceCycleId()
            ).getFinalAssessment().intValue();
            var potentialScore = 4;
            result.add(new EmployeePotentialPerformance(employee, performanceScore, potentialScore));
        }
        return result;
    }

    public void getAtGlance(@Argument Integer employeeId) {

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
