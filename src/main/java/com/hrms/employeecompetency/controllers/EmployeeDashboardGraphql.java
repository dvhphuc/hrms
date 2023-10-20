package com.hrms.employeecompetency.controllers;

import com.hrms.employeecompetency.dto.TopHighestSkillSet;
import com.hrms.employeecompetency.dto.TopSkillSetPaging;
import com.hrms.employeecompetency.models.CompetencyCycle;
import com.hrms.employeecompetency.models.SkillSetTarget;
import com.hrms.employeecompetency.models.SkillSetEvaluation;
import com.hrms.employeecompetency.services.CompetencyCycleService;
import com.hrms.employeecompetency.services.SkillSetTargetService;
import com.hrms.employeecompetency.services.SkillSetEvaluationService;
import com.hrms.employeecompetency.specifications.SkillSetEvaluationSpec;
import com.hrms.employeecompetency.specifications.SkillSetTargetSpecifications;
import com.hrms.employeemanagement.paging.Pagination;
import com.hrms.performancemanagement.dto.EmployeePerformanceRatingScore;
import com.hrms.performancemanagement.dto.PerformanceRatingScorePaging;
import com.hrms.performancemanagement.model.EmployeePerformance;
import com.hrms.performancemanagement.services.PerformanceService;
import com.hrms.performancemanagement.specifications.PerformanceSpec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.hrms.employeecompetency.controllers.CompetencyGraphql.setupPaging;

@RestController
@CrossOrigin(origins = "*")
public class EmployeeDashboardGraphql {
    SkillSetEvaluationService skillSetEvaluationService;
    SkillSetTargetService skillSetTargetService;
    PerformanceService performanceService;
    CompetencyCycleService competencyCycleService;
    @Autowired
    public EmployeeDashboardGraphql(SkillSetEvaluationService skillSetEvaluationService,
                                    SkillSetTargetService skillSetTargetService, PerformanceService performanceService,
                                    CompetencyCycleService competencyCycleService) {
        this.skillSetEvaluationService = skillSetEvaluationService;
        this.skillSetTargetService = skillSetTargetService;
        this.performanceService = performanceService;
        this.competencyCycleService = competencyCycleService;
    }

    @QueryMapping(name = "topHighestSkillSetEmployee")
    public TopSkillSetPaging getTopHighestSkillSetEmployee(@Argument(name = "employeeId") Integer empId,
                                                           @Argument Integer pageNo, @Argument Integer pageSize) {
        CompetencyCycle evalLatestCycle = skillSetEvaluationService.getLatestCycle(empId);
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
        Page<SkillSetEvaluation> ssEvaluates = skillSetEvaluationService
                                .findAll(SkillSetEvaluationSpec.getDescByCycleAndEmployee(evalLatestCycle.getId(), empId),pageable);
        List<TopHighestSkillSet> topsHighest = ssEvaluates.stream()
                .map(ssEva -> new TopHighestSkillSet(ssEva.getEmployee(),
                        ssEva.getSkillSet(),
                        ssEva.getFinalProficiencyLevel()))
                .toList();
        Pagination pagination = setupPaging(ssEvaluates, pageNo, pageSize);
        return new TopSkillSetPaging(topsHighest, pagination);
    }

    @QueryMapping(name = "topKeenSkillSetEmployee")
    public TopSkillSetPaging getTopKeenSkillSetEmployee(@Argument(name = "employeeId") Integer empId,
                                                        @Argument Integer pageNo, @Argument Integer pageSize) {
        CompetencyCycle evalLatestCycle = skillSetEvaluationService.getLatestCycle(empId);
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
        Page<SkillSetEvaluation> ssEvaluatesPage = skillSetEvaluationService
                                .findAll(SkillSetEvaluationSpec.getAscByCycleAndEmployee(evalLatestCycle.getId(), empId),pageable);
        List<TopHighestSkillSet> topsHighest = ssEvaluatesPage.stream()
                .map(ssEva -> new TopHighestSkillSet(ssEva.getEmployee(),
                        ssEva.getSkillSet(),
                        ssEva.getFinalProficiencyLevel()))
                .toList();
        Pagination pagination = setupPaging(ssEvaluatesPage, pageNo, pageSize);
        return new TopSkillSetPaging(topsHighest, pagination);
    }

    @QueryMapping(name = "topHighestSkillSetTargetEmployee")
    public TopSkillSetPaging getTopHighestSkillSetTargetEmployee(@Argument(name = "employeeId") Integer empId,
                                                                 @Argument Integer pageNo,
                                                                 @Argument Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
        CompetencyCycle compCycle = competencyCycleService.getLatestCompetencyCycle();
        Page<SkillSetTarget> ssTargets = skillSetTargetService
                                .findAll(SkillSetTargetSpecifications.getDescByCycleAndEmployee(compCycle.getId(), empId),pageable);
        List<TopHighestSkillSet> topHighestSkillSets = ssTargets.stream()
                .map(ssTarget -> new TopHighestSkillSet(ssTarget.getEmployee(),
                        ssTarget.getSkillSet(),
                        ssTarget.getTargetProficiencyLevel()))
                .toList();
        Pagination pagination = setupPaging(ssTargets, pageNo, pageSize);
        return new TopSkillSetPaging(topHighestSkillSets, pagination);
    }

    @QueryMapping(name = "employeePerformanceRatingScore")
    public PerformanceRatingScorePaging getEmployeePerformanceRatingScore(@Argument(name = "employeeId") Integer empId,
                                                                          @Argument int pageNo,
                                                                          @Argument int pageSize)
    {
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
        Page<EmployeePerformance> empPerformances =performanceService.findAll(PerformanceSpec.hasEmployeeId(empId), pageable);
        List<EmployeePerformanceRatingScore> data = empPerformances.stream()
                .map(empPerformance ->
                        new EmployeePerformanceRatingScore(empPerformance.getPerformanceCycle().getPerformanceCycleName(),
                                empPerformance.getFinalAssessment()))
                .toList();
        Pagination pagination = setupPaging(empPerformances, pageNo, pageSize);
        return new PerformanceRatingScorePaging(data, pagination);
    }
}
