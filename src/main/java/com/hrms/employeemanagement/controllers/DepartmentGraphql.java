package com.hrms.employeemanagement.controllers;

import com.hrms.employeemanagement.models.Department;
import com.hrms.employeemanagement.services.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
public class DepartmentGraphql {
    DepartmentService departmentService;
    @Autowired
    public DepartmentGraphql(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }
    @QueryMapping(name = "departments")
    public List<Department> findAllDepartments() {
        return departmentService.findAll(Specification.allOf());
    }

    @QueryMapping(name = "NumberOfDepartments")
    public Integer getNumberOfDepartments() {
        return departmentService.findAll(Specification.allOf()).size();
    }
}
