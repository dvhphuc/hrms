package com.hrms.employeemanagement.controllers.graphql;

import com.hrms.employeemanagement.models.Employee;

public record EmployeeEdge(Employee node, int cursor) {
}
