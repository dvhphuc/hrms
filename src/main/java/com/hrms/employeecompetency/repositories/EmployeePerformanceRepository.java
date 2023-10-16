package com.hrms.employeecompetency.repositories;

import com.hrms.performancemanagement.model.EmployeePerformance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface EmployeePerformanceRepository extends JpaRepository<EmployeePerformance, Integer> , JpaSpecificationExecutor<EmployeePerformance> {
}
