package com.hrms.employeecompetency.repositories;

import com.hrms.employeecompetency.models.CompetencyCycle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CompetencyCycleRepository extends JpaRepository<CompetencyCycle, Integer>,
        JpaSpecificationExecutor<CompetencyCycle> {
    CompetencyCycle findFirstByOrderByStartDateDesc();
}
