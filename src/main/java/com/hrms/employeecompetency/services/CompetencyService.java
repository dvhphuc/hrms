package com.hrms.employeecompetency.services;


import com.hrms.employeecompetency.dto.EmployeeRating;

import com.hrms.employeecompetency.models.Competency;
import com.hrms.employeecompetency.models.CompetencyCycle;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public interface CompetencyService {
    List<Competency> findAll(Specification<Competency> spec);
    List<Competency> findAll(Specification<Competency> spec, Sort sort);
    Page<Competency> findAll(Specification<Competency> spec, Pageable pageable);
}
