package com.hrms.careerpathmanagement.controllers;

import com.hrms.careerpathmanagement.dto.*;
import com.hrms.careerpathmanagement.models.*;
import com.hrms.careerpathmanagement.services.CompetencyService;
import com.hrms.employeemanagement.services.EmployeeManagementService;
import jakarta.annotation.Nullable;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@Slf4j
public class CompetencyController {
    @Autowired
    EmployeeManagementService employeeManagementService;

    @Autowired
    CompetencyService competencyService;

    @QueryMapping(name = "competencyTimeLine")
    public List<CompetencyTimeLine> getCompetencyTimeLine(@Argument Integer competencyCycleId) {
        return competencyService.getCompetencyTimeline(competencyCycleId);
    }

    //TODO:DTO
    @QueryMapping(name = "departmentInComplete")
    public List<DepartmentInCompleteDTO> getAllDepartmentInComplete(@Argument Integer competencyCycleId) {
        return competencyService.getDepartmentIncompletePercent(competencyCycleId);
    }

    @QueryMapping(name = "companyInComplete")
    public List<CompanyIncompletedDTO> getCompanyInCompletePercentage(@Argument Integer competencyCycleId) {
        return competencyService.getCompanyIncompletePercent(competencyCycleId);
    }

    @QueryMapping(name = "avgCompetencyScore")
    public List<AvgCompetencyDTO> getAvgCompetencyScore(@Argument @Nullable Integer positionId,
                                                        @Argument Integer competencyCycleId) {
        return competencyService.getAvgCompetencies(positionId, competencyCycleId);
    }

    @QueryMapping(name = "competencyRadarChart")
    public RadarChartDTO getCompetencyRadarChart(@Argument List<Integer> competencyCyclesId,
                                                 @Argument Integer departmentId) {
        return competencyService.getCompetencyRadarChart(competencyCyclesId, departmentId);
    }

    @QueryMapping(name = "topHighestSkillSet")
    public SkillSetPagingDTO getTopHighestSkill(@Argument @Nullable Integer employeeId,
                                                @Argument @Nullable Integer competencyCycleId,
                                                @Argument int pageNo, @Argument int pageSize) {
        return competencyService.getHighestSkillSet(employeeId, competencyCycleId, pageNo, pageSize);
    }

    @QueryMapping(name = "employeeSkillMatrix")
    public List<EmployeeSkillMatrixDTO> getEmployeeSkillMatrix(@Argument Integer employeeId) {
        return competencyService.getEmployeeSkillMatrix(employeeId);
    }

    @QueryMapping(name = "skillMatrixOverall")
    public SkillMatrixOverallDTO getEmpSkillMatrixOverall(@Argument Integer employeeId){
        return competencyService.getSkillMatrixOverall(employeeId);
    }

    @QueryMapping(name = "topKeenSkillSetEmployee")
    public SkillSetPagingDTO getTopKeenSkillSetEmployee(@Argument Integer employeeId,
                                                        @Argument Integer pageNo, @Argument Integer pageSize) {
        return competencyService.getTopKeenSkillSetEmployee(employeeId, pageNo, pageSize);
    }

    @QueryMapping(name = "topHighestSkillSetTargetEmployee")
    public SkillSetPagingDTO getTopHighestSkillSetTargetEmployee(@Argument Integer employeeId,
                                                                 @Argument Integer pageNo,
                                                                 @Argument Integer pageSize) {
        return competencyService.getTopHighestSkillSetTargetEmployee(employeeId, pageNo, pageSize);
    }

    @QueryMapping(name = "currentEvaluation")
    public CurrentEvaluationDTO getCurrentEvaluation(@Argument Integer employeeId) {
        return competencyService.getCurrentEvaluation(employeeId);
    }

    @QueryMapping(name = "historyEvaluation")
    public List<HistoryEvaluationDTO> getHistoryEvaluations(@Argument Integer employeeId) {
        return competencyService.getHistoryEvaluations(employeeId);
    }

    @QueryMapping
    public SkillSetSummarizationDTO skillSetSummarization(@Argument Integer employeeId, @Argument Integer cycleId) {
        return competencyService.getSkillSummarization(employeeId, cycleId);
    }

    @QueryMapping(name = "companyCompetencyDiffPercent")
    public CompanyCompetencyDiffPercentDTO getCompanyCompetencyDiffPercent() {
        return competencyService.getCompanyCompetencyDiffPercent();
    }

    @QueryMapping(name = "competencyChart")
    public List<CompetencyChartDTO> getCompetencyChart() {
        return competencyService.getCompetencyChart();
    }

}
