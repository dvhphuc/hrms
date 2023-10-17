package com.hrms.performancemanagement.service.impl;

import com.hrms.performancemanagement.model.EmployeePerformance;
import com.hrms.employeecompetency.repositories.EmployeePerformanceRepository;
import com.hrms.performancemanagement.service.PerformanceService;
import jakarta.persistence.criteria.Predicate;
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
    public List<EmployeePerformance> findByPerformanceCycleId(Integer performanceCycleId, int limit) { //Integer -> int
        Specification<EmployeePerformance> spec = Specification.where(((root, query, builder) ->
                builder.equal(root.get("performanceCycle").get("performanceCycleId"), performanceCycleId)));
        Sort sort = Sort.by(Sort.Direction.DESC, "finalAssessment");
        if (limit > employeePerformanceRepository.findAll(spec, sort).size())
            return employeePerformanceRepository.findAll(spec, sort);
        return employeePerformanceRepository.findAll(spec, sort).subList(0, limit);
    }
}
