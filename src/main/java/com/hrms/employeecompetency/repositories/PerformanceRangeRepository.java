package com.hrms.employeecompetency.repositories;

import com.hrms.employeecompetency.models.PerformanceRange;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PerformanceRangeRepository extends JpaRepository<PerformanceRange, Integer> {
}
