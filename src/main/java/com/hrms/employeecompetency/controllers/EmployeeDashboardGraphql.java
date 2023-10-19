package com.hrms.employeecompetency.controllers;

import com.hrms.employeecompetency.dto.TopHighestSkillSet;
import com.hrms.employeecompetency.dto.TopHighestSkillSetPaging;
import com.hrms.employeecompetency.models.SkillSetTarget;
import com.hrms.employeecompetency.models.SkillSetEvaluation;
import com.hrms.employeecompetency.services.SkillSetTargetService;
import com.hrms.employeecompetency.services.SkillSetEvaluationService;
import com.hrms.employeecompetency.specifications.SkillSetEvaluationSpecifications;
import com.hrms.employeecompetency.specifications.SkillSetTargetSpecifications;
import com.hrms.employeemanagement.paging.Pagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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
    @Autowired
    public EmployeeDashboardGraphql(SkillSetEvaluationService skillSetEvaluationService,
                                    SkillSetTargetService skillSetTargetService) {
        this.skillSetEvaluationService = skillSetEvaluationService;
        this.skillSetTargetService = skillSetTargetService;
    }

    @QueryMapping(name = "topHighestSkillSetEmployee")
    public TopHighestSkillSetPaging getTopHighestSkillSetEmployee(@Argument Integer competencyCycleId, @Argument Integer employeeId,
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
        return new TopHighestSkillSetPaging(topHighestSkillSets, pagination, totalCount);
    }

    @QueryMapping(name = "topHighestSkillSetTargetEmployee")
    public TopHighestSkillSetPaging getTopHighestSkillSetTargetEmployee(@Argument Integer competencyCycleId,
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
        return new TopHighestSkillSetPaging(topHighestSkillSets, pagination, totalCount);
    }
}
