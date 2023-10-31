package com.hrms.competencymanagement.repositories;

import com.hrms.competencymanagement.models.CompetencyCycle;
import com.hrms.competencymanagement.models.SkillSetEvaluation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SkillSetEvaluationRepository extends JpaRepository<SkillSetEvaluation, Integer>, JpaSpecificationExecutor<SkillSetEvaluation> {

}
