package com.hrms.performancemanagement.service;

import com.hrms.performancemanagement.model.EmployeePerformance;

import java.util.List;

public interface PerformanceService {
    List<EmployeePerformance> findAllByPerformanceCycleId(Integer performanceId);

}
