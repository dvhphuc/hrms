package com.hrms.careerpathmanagement.controllers;

import com.hrms.careerpathmanagement.dto.*;
import com.hrms.careerpathmanagement.models.*;
import com.hrms.careerpathmanagement.repositories.CompetencyCycleRepository;
import com.hrms.careerpathmanagement.repositories.CompetencyRepository;
import com.hrms.careerpathmanagement.repositories.ProficiencyLevelRepository;
import com.hrms.careerpathmanagement.services.CompetencyService;
import com.hrms.damservice.models.SourceFile;
import com.hrms.damservice.services.SourceFileService;
import com.hrms.global.paging.Pagination;
import com.hrms.employeemanagement.repositories.DepartmentRepository;
import com.hrms.employeemanagement.repositories.JobLevelRepository;
import com.hrms.employeemanagement.services.EmployeeManagementService;
import jakarta.annotation.Nullable;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@Slf4j
public class CompetencyController {
    EmployeeManagementService employeeManagementService;
    CompetencyService competencyService;
    DepartmentRepository departmentRepository;
    JobLevelRepository jobLevelRepository;
    CompetencyCycleRepository competencyCycleRepository;
    CompetencyRepository competencyRepository;
    ProficiencyLevelRepository proficiencyLevelRepository;

    SourceFileService sourceFileService;

    @Autowired
    public CompetencyController(EmployeeManagementService employeeManagementService,
                                CompetencyService competencyService, DepartmentRepository departmentRepository,
                                JobLevelRepository jobLevelRepository, CompetencyCycleRepository competencyCycleRepository,
                                CompetencyRepository competencyRepository, ProficiencyLevelRepository proficiencyLevelRepository,
                                SourceFileService sourceFileService) {
        this.employeeManagementService = employeeManagementService;
        this.competencyService = competencyService;
        this.departmentRepository = departmentRepository;
        this.jobLevelRepository = jobLevelRepository;
        this.competencyCycleRepository = competencyCycleRepository;
        this.competencyRepository = competencyRepository;
        this.proficiencyLevelRepository = proficiencyLevelRepository;
        this.sourceFileService = sourceFileService;
    }

    public static <T> Pagination setupPaging(Page<T> page, Integer pageNo, Integer pageSize) {
        long totalCount = page.getTotalElements();
        long numberOfPages = (long) Math.ceil(((double) totalCount) / pageSize);
        return new Pagination(pageNo, pageSize, totalCount, numberOfPages);
    }

    @QueryMapping(name = "competencyCycles")
    public List<CompetencyCycle> getCompetencyCycles() {
        return competencyCycleRepository.findAll();
    }

    @QueryMapping(name = "competencyTimeLine")
    public List<CompetencyTimeLine> getCompetencyTimeLine(@Argument Integer competencyCycleId) {
        return competencyService.findCompetencyTimeline(competencyCycleId);
    }

    @QueryMapping(name = "departmentInComplete")
    public List<DepartmentInComplete> getAllDepartmentInComplete(@Argument Integer competencyCycleId) {
        return competencyService.getDepartmentIncompletePercent(competencyCycleId);
    }

    @QueryMapping(name = "companyInComplete")
    public List<CompanyEvaPercent> getCompanyInCompletePercentage(@Argument Integer competencyCycleId) {
        return competencyService.getCompanyIncompletePercent(competencyCycleId);
    }

    @QueryMapping(name = "competencies")
    public List<Competency> getCompetencies() {
        return competencyRepository.findAll();
    }

    @QueryMapping(name = "proficiencyLevels")
    public List<ProficiencyLevel> getProficiencyLevels() {
        return proficiencyLevelRepository.findAll();
    }

    @QueryMapping(name = "avgCompetencyScore")
    public List<AvgCompetency> getAvgCompetencyScore(@Argument @Nullable Integer positionId, @Argument Integer competencyCycleId) {
        return competencyService.getAvgCompetencies(positionId, competencyCycleId);
    }

    @QueryMapping(name = "competencyRadarChart")
    public RadarChart getCompetencyRadarChart(@Argument List<Integer> competencyCyclesId,
                                              @Argument Integer departmentId) {
        return competencyService.getCompetencyRadarChart(competencyCyclesId, departmentId);
    }

    @QueryMapping(name = "topHighestSkillSet")
    public TopSkillSetPaging getTopHighestSkill(@Argument(name = "employeeId") @Nullable Integer empId,
                                                @Argument Integer competencyCycleId,
                                                @Argument int pageNo, @Argument int pageSize) {
        return competencyService.getTopHighestSkillSet(empId, competencyCycleId, pageNo, pageSize);
    }

    @QueryMapping(name = "employeeSkillMatrix")
    public List<EmployeeSkillMatrix> getEmployeeSkillMatrix(@Argument(name = "employeeId") Integer empId) {
        return competencyService.getEmployeeSkillMatrix(empId);
    }

    @QueryMapping(name = "skillMatrixOverall")
    public SkillMatrixOverall getEmpSkillMatrixOverall(@Argument(name = "employeeId") Integer empId){
        return competencyService.getSkillMatrixOverall(empId);
    }

    @QueryMapping(name = "topKeenSkillSetEmployee")
    public TopSkillSetPaging getTopKeenSkillSetEmployee(@Argument(name = "employeeId") Integer empId,
                                                        @Argument Integer pageNo, @Argument Integer pageSize) {
        return competencyService.getTopKeenSkillSetEmployee(empId, pageNo, pageSize);
    }

    @QueryMapping(name = "topHighestSkillSetTargetEmployee")
    public TopSkillSetPaging getTopHighestSkillSetTargetEmployee(@Argument(name = "employeeId") Integer empId,
                                                                 @Argument Integer pageNo,
                                                                 @Argument Integer pageSize) {
        return competencyService.getTopHighestSkillSetTargetEmployee(empId, pageNo, pageSize);
    }

    @QueryMapping(name = "currentEvaluation")
    public CurrentEvaluation getCurrentEvaluation(@Argument("employeeId") Integer empId) {
        return competencyService.getCurrentEvaluation(empId);
    }

    @QueryMapping
    public List<SourceFile> getQualifications(@Argument Integer employeeId, @Argument @Nullable String search, @Argument String sort)
    {
        return sourceFileService.getQualifications(employeeId, search, sort);
    }

    @QueryMapping(name = "historyEvaluation")
    public List<HistoryEvaluation> getHistoryEvaluations(@Argument("employeeId" ) Integer empId) {
        return competencyService.getHistoryEvaluations(empId);
    }

    @QueryMapping
    public SkillSetSummarization skillSetSummarization(@Argument Integer employeeId, @Argument Integer cycleId) {
        return competencyService.getSkillSummarization(employeeId, cycleId);
    }

    @QueryMapping(name = "companyCompetencyDiffPercent")
    public CompanyCompetencyDiffPercent getCompanyCompetencyDiffPercent() {
        return competencyService.getCompanyCompetencyDiffPercent();
    }

    @QueryMapping(name = "competencyChart")
    public List<CompetencyChart> getCompetencyChart() {
        return competencyService.getCompetencyChart();
    }

}
