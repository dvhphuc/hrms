package com.hrms.performancemanagement.service.impl;

import com.hrms.performancemanagement.model.EmployeePerformance;
import com.hrms.employeecompetency.repositories.EmployeePerformanceRepository;
import com.hrms.performancemanagement.service.PerformanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PerformanceServiceImpl implements PerformanceService {
    @Autowired
    EmployeePerformanceRepository employeePerformanceRepository;

    @Override
    public List<EmployeePerformance> findAllByPerformanceCycleId(Integer performanceCycleId) {
        Specification<EmployeePerformance> spec = Specification.where(((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("performanceCycleId"), performanceCycleId)));
        Sort sort = Sort.by(Sort.Direction.DESC, "finalAssessment");
        return employeePerformanceRepository.findAll(spec, sort);
    }
}
