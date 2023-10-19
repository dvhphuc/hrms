package com.hrms.performancemanagement.service;

import com.hrms.performancemanagement.model.EmployeePerformance;
import com.hrms.performancemanagement.model.PerformanceCycle;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public interface PerformanceService {
    Integer countByRatingRangeAndPerformanceCycleId(Integer min, Integer max, Integer performanceCycleId);
    List<EmployeePerformance> findByPerformanceCycleId(Integer performanceId, Integer limit);
    Page<EmployeePerformance> findAll(Pageable pageable);
    EmployeePerformance findByEmployeeIdAndPerformanceCyclePerformanceCycleId(int employeeId, int performanceCycleId);
    PerformanceCycle findLatestPerformanceCycleOfEmployee(int employeeId);
}