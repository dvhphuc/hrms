package com.hrms.employeemanagement.dto;

import com.hrms.employeemanagement.models.Employee;


public record EmployeeSource(Employee employee, String uri) {
}
