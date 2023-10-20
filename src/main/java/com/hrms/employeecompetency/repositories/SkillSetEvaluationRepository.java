package com.hrms.employeecompetency.repositories;

import com.hrms.employeecompetency.models.CompetencyCycle;
import com.hrms.employeecompetency.models.SkillSetEvaluation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

public interface SkillSetEvaluationRepository extends JpaRepository<SkillSetEvaluation, Integer>, JpaSpecificationExecutor<SkillSetEvaluation> {
    @Query("SELECT s.competencyCycle FROM SkillSetEvaluation s WHERE s.employee.id = ?1 AND s.finalProficiencyLevel IS NOT NULL ORDER BY s.competencyCycle.startDate DESC LIMIT 1")
    CompetencyCycle selectEvaluateCompetencyCycle(Integer employeeId);
}
