package com.hrms.employeemanagement.services;

import com.hrms.employeemanagement.models.Department;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public interface DepartmentService {
    List<Department> findAll(Specification<Department> spec);
    List<Department> findAll(Specification<Department> spec, Sort sort);
    Page<Department> findAll(Specification<Department> spec, Pageable pageable);
}
