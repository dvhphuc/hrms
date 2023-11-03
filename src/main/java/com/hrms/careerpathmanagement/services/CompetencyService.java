package com.hrms.careerpathmanagement.services;

import com.hrms.careerpathmanagement.dto.*;
import com.hrms.careerpathmanagement.models.CompetencyEvaluation;
import com.hrms.careerpathmanagement.models.CompetencyTimeLine;
import jakarta.annotation.Nullable;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.List;

public interface CompetencyService {
    @Scheduled(cron = "0 0 0 * * *")
    void updateIsDoneForOverdueItems();
    List<CompetencyTimeLine> findCompetencyTimeline(Integer competencyCycleId);
    List<DepartmentInCompleteDTO> getDepartmentIncompletePercent(Integer competencyCycleId);
    List<CompanyEvaPercentDTO> getCompanyIncompletePercent(Integer competencyCycleId);
    List<CompetencyEvaluation> findByPositionAndCycle(Integer positionId, Integer competencyCycleId);
    List<CompetencyEvaluation> findByCycle(Integer competencyCycleId);
    List<CompetencyEvaluation> findByCyclesAndDepartment(List<Integer> competencyCycleIds, Integer departmentId);
    List<AvgCompetencyDTO> getAvgCompetencies(Integer positionId, Integer competencyCycleId);
    RadarChart getCompetencyRadarChart(List<Integer> competencyCyclesId, Integer departmentId);
    SkillSetPagingDTO getTopHighestSkillSet(@Nullable Integer empId, @Nullable Integer competencyCycleId, int pageNo, int pageSize);
    List<EmployeeSkillMatrix> getEmployeeSkillMatrix(Integer employeeId);
    SkillMatrixOverall getSkillMatrixOverall(Integer employeeId);
    SkillSetPagingDTO getTopKeenSkillSetEmployee(Integer employeeId, int pageNo, int pageSize);
    SkillSetPagingDTO getTopHighestSkillSetTargetEmployee(Integer employeeId, int pageNo, int pageSize);
    CurrentEvaluation getCurrentEvaluation(Integer employeeId);
    List<HistoryEvaluation> getHistoryEvaluations(Integer employeeId);

    SkillSetSummarizationDTO getSkillSummarization(Integer employeeId, Integer cycleId);


    CompanyCompetencyDiffPercent getCompanyCompetencyDiffPercent();

    List<CompetencyChart> getCompetencyChart();
}
