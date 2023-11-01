package com.hrms.careerpathmanagement.repositories;

import com.hrms.careerpathmanagement.models.CompetencyEvaluation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CompetencyEvaluationRepository extends JpaRepository<CompetencyEvaluation, Integer>, JpaSpecificationExecutor<CompetencyEvaluation> {
}
