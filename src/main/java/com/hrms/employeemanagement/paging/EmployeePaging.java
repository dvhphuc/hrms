package com.hrms.employeemanagement.paging;


import com.hrms.employeemanagement.models.Employee;

import java.util.List;


public record EmployeePaging(List<Employee> data, Pagination pagination, long totalCount) {
}
