package com.hrms.competencymanagement.repositories;

import com.hrms.competencymanagement.models.PerformanceRange;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PerformanceRangeRepository extends JpaRepository<PerformanceRange, Integer> {
}
