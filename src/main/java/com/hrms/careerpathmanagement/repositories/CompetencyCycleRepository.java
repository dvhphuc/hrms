package com.hrms.careerpathmanagement.repositories;

import com.hrms.careerpathmanagement.models.CompetencyCycle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CompetencyCycleRepository extends JpaRepository<CompetencyCycle, Integer>,
        JpaSpecificationExecutor<CompetencyCycle> {
    CompetencyCycle findFirstByOrderByStartDateDesc();

    CompetencyCycle findByYear(Integer year);
}
