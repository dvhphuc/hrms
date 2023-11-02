package com.hrms.performancemanagement.repositories;

import com.hrms.performancemanagement.model.PerformanceCycle;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface PerformanceCycleRepository extends JpaRepository<PerformanceCycle, Integer>, JpaSpecificationExecutor<PerformanceCycle> {
    List<PerformanceCycle> findAll(Sort sort);
    PerformanceCycle findFirstByOrderByPerformanceCycleIdDesc();
}
