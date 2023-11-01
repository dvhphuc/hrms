package com.hrms.careerpathmanagement.repositories;

import com.hrms.performancemanagement.model.EmployeePerformance;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface PerformanceEvaluationRepository extends JpaRepository<EmployeePerformance, Integer> , JpaSpecificationExecutor<EmployeePerformance> {
    List<EmployeePerformance> findAll(Specification spec);
    Page<EmployeePerformance> findAll(Pageable pageable);
    List<EmployeePerformance> findByEmployeeIdAndPerformanceCyclePerformanceCycleId(int employeeId, int performanceCycleId);
}
