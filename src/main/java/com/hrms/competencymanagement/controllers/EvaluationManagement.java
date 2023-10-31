package com.hrms.competencymanagement.controllers;

import com.hrms.competencymanagement.dto.*;
import com.hrms.competencymanagement.models.*;
import com.hrms.competencymanagement.repositories.CompetencyCycleRepository;
import com.hrms.competencymanagement.repositories.CompetencyRepository;
import com.hrms.competencymanagement.repositories.ProficiencyLevelRepository;
import com.hrms.competencymanagement.services.EvaluationService;
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
public class EvaluationManagement {
    EmployeeManagementService employeeManagementService;
    EvaluationService evaluationService;
    DepartmentRepository departmentRepository;
    JobLevelRepository jobLevelRepository;
    CompetencyCycleRepository competencyCycleRepository;
    CompetencyRepository competencyRepository;
    ProficiencyLevelRepository proficiencyLevelRepository;

    @Autowired
    public EvaluationManagement(EmployeeManagementService employeeManagementService,
                                EvaluationService evaluationService, DepartmentRepository departmentRepository,
                                JobLevelRepository jobLevelRepository, CompetencyCycleRepository competencyCycleRepository,
                                CompetencyRepository competencyRepository, ProficiencyLevelRepository proficiencyLevelRepository) {
        this.employeeManagementService = employeeManagementService;
        this.evaluationService = evaluationService;
        this.departmentRepository = departmentRepository;
        this.jobLevelRepository = jobLevelRepository;
        this.competencyCycleRepository = competencyCycleRepository;
        this.competencyRepository = competencyRepository;
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
        return evaluationService.findCompetencyTimeline(competencyCycleId);
    }

    @QueryMapping(name = "departmentInComplete")
    public List<DepartmentInComplete> getAllDepartmentInComplete(@Argument Integer competencyCycleId) {
        return evaluationService.getDepartmentIncompletePercent(competencyCycleId);
    }

    @QueryMapping(name = "companyInComplete")
    public List<CompanyEvaPercent> getCompanyInCompletePercentage(@Argument Integer competencyCycleId) {
        return evaluationService.getCompanyIncompletePercent(competencyCycleId);
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
        return evaluationService.getAvgCompetencies(positionId, competencyCycleId);
    }

    @QueryMapping(name = "competencyRadarChart")
    public RadarChart getCompetencyRadarChart(@Argument List<Integer> competencyCyclesId,
                                              @Argument Integer departmentId) {
        return evaluationService.getCompetencyRadarChart(competencyCyclesId, departmentId);
    }

    @QueryMapping(name = "topHighestSkillSet")
    public TopSkillSetPaging getTopHighestSkill(@Argument(name = "employeeId") @Nullable Integer empId,
                                                @Argument Integer competencyCycleId,
                                                @Argument int pageNo, @Argument int pageSize) {
        return evaluationService.getTopHighestSkillSet(empId, competencyCycleId, pageNo, pageSize);
    }

    @QueryMapping(name = "employeeSkillMatrix")
    public List<EmployeeSkillMatrix> getEmployeeSkillMatrix(@Argument(name = "employeeId") Integer empId) {
        return evaluationService.getEmployeeSkillMatrix(empId);
    }

    @QueryMapping(name = "skillMatrixOverall")
    public SkillMatrixOverall getEmpSkillMatrixOverall(@Argument(name = "employeeId") Integer empId){
        return evaluationService.getSkillMatrixOverall(empId);
    }

    @QueryMapping(name = "topKeenSkillSetEmployee")
    public TopSkillSetPaging getTopKeenSkillSetEmployee(@Argument(name = "employeeId") Integer empId,
                                                        @Argument Integer pageNo, @Argument Integer pageSize) {
        return evaluationService.getTopKeenSkillSetEmployee(empId, pageNo, pageSize);
    }

    @QueryMapping(name = "topHighestSkillSetTargetEmployee")
    public TopSkillSetPaging getTopHighestSkillSetTargetEmployee(@Argument(name = "employeeId") Integer empId,
                                                                 @Argument Integer pageNo,
                                                                 @Argument Integer pageSize) {
        return evaluationService.getTopHighestSkillSetTargetEmployee(empId, pageNo, pageSize);
    }

    @QueryMapping(name = "currentEvaluation")
    public CurrentEvaluation getCurrentEvaluation(@Argument("employeeId") Integer empId) {
        return evaluationService.getCurrentEvaluation(empId);
    }

    @QueryMapping(name = "historyEvaluation")
    public List<HistoryEvaluation> getHistoryEvaluations(@Argument("employeeId" ) Integer empId) {
        return evaluationService.getHistoryEvaluations(empId);
    }


}
