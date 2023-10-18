package com.hrms.employeecompetency.services;

import com.hrms.employeecompetency.models.CompetencyCycle;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public interface CompetencyCycleService {
    List<CompetencyCycle> findAll(Specification<CompetencyCycle> spec);
    List<CompetencyCycle> findAll(Specification<CompetencyCycle> spec, Sort sort);
    Page<CompetencyCycle> findAll(Specification<CompetencyCycle> spec, Pageable pageable);

    CompetencyCycle getLatestCompetencyCycle();
}
