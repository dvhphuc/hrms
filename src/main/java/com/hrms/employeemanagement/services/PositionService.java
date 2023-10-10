package com.hrms.employeemanagement.services;


import com.hrms.employeemanagement.models.Position;
import com.hrms.employeemanagement.models.PositionLevel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Optional;

public interface PositionService {
    List<Position> findAll(Specification<Position> spec);
    List<Position> findAll(Specification<Position> spec, Sort sort);
    Page<Position> findAll(Specification<Position> spec, Pageable pageable);
}
