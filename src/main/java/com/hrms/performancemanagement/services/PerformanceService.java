package com.hrms.performancemanagement.services;

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
    List<EmployeePerformance> findAll(Specification<EmployeePerformance> spec);
    List<EmployeePerformance> findAll(Specification<EmployeePerformance> spec, Sort sort);
    Page<EmployeePerformance> findAll(Specification<EmployeePerformance> spec, Pageable pageable);

    List<EmployeePerformance> findAllByPositionIdAndPerformanceCycleId(Integer positionId, Integer performanceCycleId);

    List<EmployeePerformance> filterCategory(String category, List<EmployeePerformance> employeePerformances);
}
