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
        return competencyService.findCompetencyTimeline(competencyCycleId);
    }

    //TODO:DTO
    @QueryMapping(name = "departmentInComplete")
    public List<DepartmentInCompleteDTO> getAllDepartmentInComplete(@Argument Integer competencyCycleId) {
        return competencyService.getDepartmentIncompletePercent(competencyCycleId);
    }

    @QueryMapping(name = "companyInComplete")
    public List<CompanyEvaPercentDTO> getCompanyInCompletePercentage(@Argument Integer competencyCycleId) {
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
    public List<EmployeeSkillMatrixDTO> getEmployeeSkillMatrix(@Argument(name = "employeeId") Integer empId) {
        return competencyService.getEmployeeSkillMatrix(empId);
    }

    @QueryMapping(name = "skillMatrixOverall")
    public SkillMatrixOverallDTO getEmpSkillMatrixOverall(@Argument(name = "employeeId") Integer empId){
        return competencyService.getSkillMatrixOverall(empId);
    }

    @QueryMapping(name = "topKeenSkillSetEmployee")
    public SkillSetPagingDTO getTopKeenSkillSetEmployee(@Argument(name = "employeeId") Integer empId,
                                                        @Argument Integer pageNo, @Argument Integer pageSize) {
        return competencyService.getTopKeenSkillSetEmployee(empId, pageNo, pageSize);
    }

    @QueryMapping(name = "topHighestSkillSetTargetEmployee")
    public SkillSetPagingDTO getTopHighestSkillSetTargetEmployee(@Argument(name = "employeeId") Integer empId,
                                                                 @Argument Integer pageNo,
                                                                 @Argument Integer pageSize) {
        return competencyService.getTopHighestSkillSetTargetEmployee(empId, pageNo, pageSize);
    }

    @QueryMapping(name = "currentEvaluation")
    public CurrentEvaluationDTO getCurrentEvaluation(@Argument("employeeId") Integer empId) {
        return competencyService.getCurrentEvaluation(empId);
    }

//    @QueryMapping
//    public List<SourceFile> getQualifications(@Argument Integer employeeId, @Argument @Nullable String search, @Argument String sort)
//    {
//        return damService.getQualifications(employeeId, search, sort);
//    }

    @QueryMapping(name = "historyEvaluation")
    public List<HistoryEvaluationDTO> getHistoryEvaluations(@Argument("employeeId" ) Integer empId) {
        return competencyService.getHistoryEvaluations(empId);
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
