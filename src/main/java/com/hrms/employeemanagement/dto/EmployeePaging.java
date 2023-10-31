package com.hrms.employeemanagement.dto;


import com.hrms.employeemanagement.models.Employee;
import com.hrms.global.paging.Pagination;

import java.util.List;


public record EmployeePaging(List<Employee> data, Pagination pagination) {
}
