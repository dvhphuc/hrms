package com.hrms.employeecompetency.repositories;

import com.hrms.performancemanagement.model.PerformanceCycle;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PerformanceCycleRepository extends JpaRepository<PerformanceCycle, Integer> {
    List<PerformanceCycle> findAll(Sort sort);
}
