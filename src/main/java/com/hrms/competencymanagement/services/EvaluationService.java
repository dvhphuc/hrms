package com.hrms.competencymanagement.services;

import com.hrms.competencymanagement.dto.*;
import com.hrms.competencymanagement.models.CompetencyEvaluation;
import com.hrms.competencymanagement.models.CompetencyTimeLine;
import jakarta.annotation.Nullable;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.List;

public interface EvaluationService {
    @Scheduled(cron = "0 0 0 * * *")
    void updateIsDoneForOverdueItems();
    List<CompetencyTimeLine> findCompetencyTimeline(Integer competencyCycleId);
    List<DepartmentInComplete> getDepartmentIncompletePercent(Integer competencyCycleId);
    List<CompanyEvaPercent> getCompanyIncompletePercent(Integer competencyCycleId);
    List<CompetencyEvaluation> findByPositionAndCycle(Integer positionId, Integer competencyCycleId);
    List<CompetencyEvaluation> findByCycle(Integer competencyCycleId);
    List<CompetencyEvaluation> findByCyclesAndDepartment(List<Integer> competencyCycleIds, Integer departmentId);
    List<AvgCompetency> getAvgCompetencies(Integer positionId, Integer competencyCycleId);
    RadarChart getCompetencyRadarChart(List<Integer> competencyCyclesId, Integer departmentId);
    TopSkillSetPaging getTopHighestSkillSet(@Nullable Integer empId, Integer competencyCycleId, int pageNo, int pageSize);
    List<EmployeeSkillMatrix> getEmployeeSkillMatrix(Integer employeeId);
    SkillMatrixOverall getSkillMatrixOverall(Integer employeeId);
    TopSkillSetPaging getTopKeenSkillSetEmployee(Integer employeeId, int pageNo, int pageSize);
    TopSkillSetPaging getTopHighestSkillSetTargetEmployee(Integer employeeId, int pageNo, int pageSize);
    CurrentEvaluation getCurrentEvaluation(Integer employeeId);
    List<HistoryEvaluation> getHistoryEvaluations(Integer employeeId);
}
