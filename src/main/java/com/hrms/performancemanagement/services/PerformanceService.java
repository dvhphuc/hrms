package com.hrms.performancemanagement.services;

import com.hrms.performancemanagement.model.EmployeePerformance;
import com.hrms.performancemanagement.model.PerformanceCycle;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PerformanceService {
    List<PerformanceCycle> getPerformanceCycles();
    Page<EmployeePerformance> getPerformances(Integer empId, Pageable pageable);
}
