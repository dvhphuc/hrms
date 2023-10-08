package com.hrms.employeemanagement.services.impl;

import com.hrms.employeemanagement.models.Department;
import com.hrms.employeemanagement.repositories.DepartmentRepository;
import com.hrms.employeemanagement.repositories.PositionLevelRepository;
import com.hrms.employeemanagement.services.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class DepartmentServiceImpl implements DepartmentService {
    @Autowired
    private DepartmentRepository departmentRepository;
    @Override
    public List<Department> findAll(Specification<Department> spec) {
        return departmentRepository.findAll(spec);
    }

    @Override
    public List<Department> findAll(Specification<Department> spec, Sort sort) {
        return departmentRepository.findAll(spec, sort);
    }

    @Override
    public Page<Department> findAll(Specification<Department> spec, Pageable pageable) {
        return departmentRepository.findAll(spec, pageable);
    }
}
