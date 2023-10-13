package com.hrms.employeecompetency.services.impl;

import com.hrms.employeecompetency.models.EvaluationOverall;
import com.hrms.employeecompetency.repositories.EvaluationOverallRepository;
import com.hrms.employeecompetency.services.EvaluationOverallService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EvaluationOverallServiceImpl implements EvaluationOverallService {
    @Autowired
    private EvaluationOverallRepository evaluationOverallRepository;
    @Override
    public List<EvaluationOverall> findAll(Specification<EvaluationOverall> spec) {
        return evaluationOverallRepository.findAll(spec);
    }

    @Override
    public List<EvaluationOverall> findAll(Specification<EvaluationOverall> spec, Sort sort) {
        return evaluationOverallRepository.findAll(spec, sort);
    }

    @Override
    public Page<EvaluationOverall> findAll(Specification<EvaluationOverall> spec, Pageable pageable) {
        return evaluationOverallRepository.findAll(spec, pageable);
    }
}
