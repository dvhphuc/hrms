package com.hrms.performancemanagement.services;

import com.hrms.performancemanagement.model.PerformanceCycle;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public interface PerformanceCycleService {
    List<PerformanceCycle> findAll(Specification<PerformanceCycle> spec);
    List<PerformanceCycle> findAll(Specification<PerformanceCycle> spec, Sort sort);
    Page<PerformanceCycle> findAll(Specification<PerformanceCycle> spec, Pageable pageable);
}
