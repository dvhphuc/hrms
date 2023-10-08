package com.hrms.employeemanagement.services;

import com.hrms.employeemanagement.models.PositionLevel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public interface PositionLevelService {
    List<PositionLevel> findAll(Specification<PositionLevel> spec);
    List<PositionLevel> findAll(Specification<PositionLevel> spec, Sort sort);
    Page<PositionLevel> findAll(Specification<PositionLevel> spec, Pageable pageable);
}
