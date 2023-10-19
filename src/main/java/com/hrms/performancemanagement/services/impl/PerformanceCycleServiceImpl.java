package com.hrms.performancemanagement.services.impl;

import com.hrms.performancemanagement.model.EmployeePerformance;
import com.hrms.performancemanagement.model.PerformanceCycle;
import com.hrms.performancemanagement.repositories.PerformanceCycleRepository;
import com.hrms.performancemanagement.services.PerformanceCycleService;
import com.hrms.performancemanagement.services.PerformanceService;
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
public class PerformanceCycleServiceImpl implements PerformanceCycleService {
    @Autowired
    PerformanceCycleRepository performanceCycleRepository;

    @Override
    public List<PerformanceCycle> findAll(Specification<PerformanceCycle> spec) {
        return performanceCycleRepository.findAll(spec);
    }

    @Override
    public List<PerformanceCycle> findAll(Specification<PerformanceCycle> spec, Sort sort) {
        return performanceCycleRepository.findAll(spec, sort);
    }

    @Override
    public Page<PerformanceCycle> findAll(Specification<PerformanceCycle> spec, Pageable pageable) {
        return performanceCycleRepository.findAll(spec, pageable);
    }
}
