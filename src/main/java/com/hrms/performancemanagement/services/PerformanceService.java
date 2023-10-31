package com.hrms.performancemanagement.services;

import com.hrms.performancemanagement.model.EmployeePerformance;
import com.hrms.performancemanagement.model.PerformanceCycle;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public interface PerformanceService {
    List<PerformanceCycle> getPerformanceCycles();
    Page<EmployeePerformance> findEmpPerformances(Integer empId, Pageable pageable);
}
