package com.hrms.employeecompetency.services;

import com.hrms.employeecompetency.models.EvaluationOverall;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public interface EvaluationOverallService {
    List<EvaluationOverall> findAll(Specification<EvaluationOverall> spec);
    List<EvaluationOverall> findAll(Specification<EvaluationOverall> spec, Sort sort);
    Page<EvaluationOverall> findAll(Specification<EvaluationOverall> spec, Pageable pageable);
}
