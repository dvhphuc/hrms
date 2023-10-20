package com.hrms.employeecompetency.services.impl;

import com.hrms.employeecompetency.models.CompetencyCycle;
import com.hrms.employeecompetency.models.SkillSetEvaluation;
import com.hrms.employeecompetency.repositories.SkillSetEvaluationRepository;
import com.hrms.employeecompetency.services.SkillSetEvaluationService;
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
public class SkillSetEvaluationServiceImpl implements SkillSetEvaluationService {
    @Autowired
    private SkillSetEvaluationRepository skillSetEvaluationRepository;

    @Override
    public List<SkillSetEvaluation> findAll(Specification<SkillSetEvaluation> spec) {
        return skillSetEvaluationRepository.findAll(spec);
    }

    @Override
    public List<SkillSetEvaluation> findAll(Specification<SkillSetEvaluation> spec, Sort sort) {
        return skillSetEvaluationRepository.findAll(spec, sort);
    }

    @Override
    public Page<SkillSetEvaluation> findAll(Specification<SkillSetEvaluation> spec, Pageable pageable) {
        return skillSetEvaluationRepository.findAll(spec, pageable);
    }

    @Override
    public List<SkillSetEvaluation> findAllByEmployeeId(Integer employeeId) {
        var skillSetOfEmployee = skillSetEvaluationRepository.findAllByEmployeeId(employeeId);
        return skillSetOfEmployee;
    }

    public CompetencyCycle getLatestCycle(Integer employeeId) {
        return skillSetEvaluationRepository.selectEvaluateCompetencyCycle(employeeId);
    }
}
