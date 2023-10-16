package com.hrms.employeecompetency.repositories;

import com.hrms.employeecompetency.models.CompetencyEvaluation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

public interface CompetencyEvaluationRepository extends JpaRepository<CompetencyEvaluation, Integer>, JpaSpecificationExecutor<CompetencyEvaluation> {
}
