package com.hrms.performancemanagement.services.impl;

import com.hrms.performancemanagement.model.EmployeePerformance;
import com.hrms.competencymanagement.repositories.EmployeePerformanceRepository;
import com.hrms.performancemanagement.model.PerformanceCycle;
import com.hrms.performancemanagement.repositories.PerformanceCycleRepository;
import com.hrms.performancemanagement.services.PerformanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PerformanceServiceImpl implements PerformanceService {
    @Autowired
    EmployeePerformanceRepository employeePerformanceRepository;

    @Autowired
    PerformanceCycleRepository performanceCycleRepository;

    @Override
    public List<PerformanceCycle> getPerformanceCycles() {
        return performanceCycleRepository.findAll();
    }

    @Override
    public Page<EmployeePerformance> findEmpPerformances(Integer empId, Pageable pageable) {
        Specification<EmployeePerformance> spec = ((root, query, criteriaBuilder)
                -> criteriaBuilder.equal(root.get("employee").get("id"), empId));
        return employeePerformanceRepository.findAll(spec, pageable);
    }
}
