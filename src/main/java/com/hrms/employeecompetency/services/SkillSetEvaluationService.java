package com.hrms.employeecompetency.services;

import com.hrms.employeecompetency.models.SkillSetEvaluation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public interface SkillSetEvaluationService {
    List<SkillSetEvaluation> findAll(Specification<SkillSetEvaluation> spec);
    List<SkillSetEvaluation> findAll(Specification<SkillSetEvaluation> spec, Sort sort);
    Page<SkillSetEvaluation> findAll(Specification<SkillSetEvaluation> spec, Pageable pageable);
}
