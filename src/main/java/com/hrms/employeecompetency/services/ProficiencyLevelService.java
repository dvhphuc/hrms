package com.hrms.employeecompetency.services;

import com.hrms.employeecompetency.models.ProficiencyLevel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public interface ProficiencyLevelService {
    List<ProficiencyLevel> findAll(Specification<ProficiencyLevel> spec);
    List<ProficiencyLevel> findAll(Specification<ProficiencyLevel> spec, Sort sort);
    Page<ProficiencyLevel> findAll(Specification<ProficiencyLevel> spec, Pageable pageable);
}
