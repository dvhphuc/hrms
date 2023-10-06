package com.hrms.employeemanagement.controllers.graphql;


import java.util.List;


public record EmployeeConnection(List<EmployeeEdge> edges, Pagination pagination, long totalCount) {
}
