package com.hrms.employeemanagement.controllers.graphql;


import com.hrms.employeemanagement.models.Employee;

import java.util.List;


public record EmployeeConnection(List<Employee> data, Pagination pagination, long totalCount) {
}
