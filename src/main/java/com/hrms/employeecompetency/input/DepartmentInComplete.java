package com.hrms.employeecompetency.input;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

public class DepartmentInComplete {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String departmentName;
}
