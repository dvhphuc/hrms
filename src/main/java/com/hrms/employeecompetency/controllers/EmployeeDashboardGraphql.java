package com.hrms.employeecompetency.controllers;

import com.hrms.employeecompetency.dto.TopHighestSkillSet;
import com.hrms.employeecompetency.dto.TopSkillSetPaging;
import com.hrms.employeecompetency.models.SkillSetTarget;
import com.hrms.employeecompetency.models.SkillSetEvaluation;
import com.hrms.employeecompetency.services.SkillSetTargetService;
import com.hrms.employeecompetency.services.SkillSetEvaluationService;
import com.hrms.employeecompetency.specifications.SkillSetEvaluationSpecifications;
import com.hrms.employeecompetency.specifications.SkillSetTargetSpecifications;
import com.hrms.employeemanagement.paging.Pagination;
import com.hrms.performancemanagement.dto.EmployeePerformanceRatingScore;
import com.hrms.performancemanagement.dto.EmployeePerformanceRatingScorePaging;
import com.hrms.performancemanagement.model.EmployeePerformance;
import com.hrms.performancemanagement.services.PerformanceService;
import com.hrms.performancemanagement.specifications.PerformanceSpecifications;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
public class EmployeeDashboardGraphql {
    SkillSetEvaluationService skillSetEvaluationService;
    SkillSetTargetService skillSetTargetService;
    PerformanceService performanceService;
    @Autowired
    public EmployeeDashboardGraphql(SkillSetEvaluationService skillSetEvaluationService,
                                    SkillSetTargetService skillSetTargetService, PerformanceService performanceService) {
        this.skillSetEvaluationService = skillSetEvaluationService;
        this.skillSetTargetService = skillSetTargetService;
        this.performanceService = performanceService;
    }

    @QueryMapping(name = "topHighestSkillSetEmployee")
    public TopSkillSetPaging getTopHighestSkillSetEmployee(@Argument Integer competencyCycleId, @Argument Integer employeeId,
                                                           @Argument Integer pageNo, @Argument Integer pageSize) {
        List<TopHighestSkillSet> topHighestSkillSets = new ArrayList<>();
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
        Page<SkillSetEvaluation> skillSetEvaluations =
                skillSetEvaluationService
                        .findAll(SkillSetEvaluationSpecifications.hasTop10ProficiencyLevelOfEmployeeInCycle(competencyCycleId, employeeId),pageable);
        for (SkillSetEvaluation skillSetEvaluation : skillSetEvaluations) {
            topHighestSkillSets.add(
                    new TopHighestSkillSet(skillSetEvaluation.getEmployee(),
                            skillSetEvaluation.getPositionSkillSet().getSkillSet(),
                            skillSetEvaluation.getFinalProficiencyLevel()));
        }
        long totalCount = skillSetEvaluations.getTotalElements();
        long numberOfPages = (long) Math.ceil(((double) totalCount) / pageSize);
        Pagination pagination = new Pagination(pageNo, pageSize, totalCount, numberOfPages);
        return new TopSkillSetPaging(topHighestSkillSets, pagination, totalCount);
    }

    @QueryMapping(name = "topKeenSkillSetEmployee")
    public TopSkillSetPaging getTopKeenSkillSetEmployee(@Argument Integer competencyCycleId, @Argument Integer employeeId,
                                                        @Argument Integer pageNo, @Argument Integer pageSize) {
        List<TopHighestSkillSet> topHighestSkillSets = new ArrayList<>();
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
        Page<SkillSetEvaluation> skillSetEvaluations =
                skillSetEvaluationService
                        .findAll(SkillSetEvaluationSpecifications.hasTop10KeenProficiencyLevelOfEmployeeInCycle(competencyCycleId, employeeId),pageable);
        for (SkillSetEvaluation skillSetEvaluation : skillSetEvaluations) {
            topHighestSkillSets.add(
                    new TopHighestSkillSet(skillSetEvaluation.getEmployee(),
                            skillSetEvaluation.getPositionSkillSet().getSkillSet(),
                            skillSetEvaluation.getFinalProficiencyLevel()));
        }
        long totalCount = skillSetEvaluations.getTotalElements();
        long numberOfPages = (long) Math.ceil(((double) totalCount) / pageSize);
        Pagination pagination = new Pagination(pageNo, pageSize, totalCount, numberOfPages);
        return new TopSkillSetPaging(topHighestSkillSets, pagination, totalCount);
    }

    @QueryMapping(name = "topHighestSkillSetTargetEmployee")
    public TopSkillSetPaging getTopHighestSkillSetTargetEmployee(@Argument Integer competencyCycleId,
                                                                 @Argument Integer employeeId,
                                                                 @Argument Integer pageNo,
                                                                 @Argument Integer pageSize) {
        List<TopHighestSkillSet> topHighestSkillSets = new ArrayList<>();
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
        Page<SkillSetTarget> skillSetTargets =
                skillSetTargetService
                        .findAll(SkillSetTargetSpecifications.hasTop10TargetProficiencyLevelOfInCycle(competencyCycleId, employeeId),pageable);
        for (SkillSetTarget skillSetTarget : skillSetTargets) {
            topHighestSkillSets.add(
                    new TopHighestSkillSet(skillSetTarget.getEmployee(),
                            skillSetTarget.getSkillSet(),
                            skillSetTarget.getTargetProficiencyLevel()));
        }
        long totalCount = skillSetTargets.getTotalElements();
        long numberOfPages = (long) Math.ceil(((double) totalCount) / pageSize);
        Pagination pagination = new Pagination(pageNo, pageSize, totalCount, numberOfPages);
        return new TopSkillSetPaging(topHighestSkillSets, pagination, totalCount);
    }

    @QueryMapping(name = "employeePerformanceRatingScore")
    public EmployeePerformanceRatingScorePaging getEmployeePerformanceRatingScore(@Argument Integer employeeId, @Argument int pageNo, @Argument int pageSize)
    {
        List<EmployeePerformanceRatingScore> data = new ArrayList<>();
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
        Page<EmployeePerformance> employeePerformances =performanceService.findAll(PerformanceSpecifications.hasEmployeeId(employeeId), pageable);
        for (EmployeePerformance employeePerformance : employeePerformances) {
            data.add(new EmployeePerformanceRatingScore(employeePerformance.getPerformanceCycle().getPerformanceCycleName(), employeePerformance.getFinalAssessment()));
        }
        long totalCount = employeePerformances.getTotalElements();
        long numberOfPages = (long) Math.ceil(((double) totalCount) / pageSize);
        Pagination pagination = new Pagination(pageNo, pageSize, totalCount, numberOfPages);
        return new EmployeePerformanceRatingScorePaging(data, pagination);
    }
}
