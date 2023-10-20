package com.hrms.employeecompetency.repositories;

import com.hrms.employeecompetency.models.CompetencyCycle;
import com.hrms.employeecompetency.models.SkillSetEvaluation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface SkillSetEvaluationRepository extends JpaRepository<SkillSetEvaluation, Integer>, JpaSpecificationExecutor<SkillSetEvaluation> {
    List<SkillSetEvaluation> findAllByEmployeeId(Integer employeeId);
}
