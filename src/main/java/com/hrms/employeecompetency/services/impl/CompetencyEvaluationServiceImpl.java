package com.hrms.employeecompetency.services.impl;

import com.hrms.employeecompetency.models.CompetencyEvaluation;
import com.hrms.employeecompetency.repositories.CompetencyEvaluationRepository;
import com.hrms.employeecompetency.services.CompetencyEvaluationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CompetencyEvaluationServiceImpl implements CompetencyEvaluationService {
    @Autowired
    private CompetencyEvaluationRepository competencyEvaluationRepository;
    @Override
    public List<CompetencyEvaluation> findAll(Specification<CompetencyEvaluation> spec) {
        return competencyEvaluationRepository.findAll(spec);
    }

    @Override
    public List<CompetencyEvaluation> findAll(Specification<CompetencyEvaluation> spec, Sort sort) {
        return competencyEvaluationRepository.findAll(spec, sort);
    }

    @Override
    public Page<CompetencyEvaluation> findAll(Specification<CompetencyEvaluation> spec, Pageable pageable) {
        return competencyEvaluationRepository.findAll(spec, pageable);
    }
}
