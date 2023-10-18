package com.hrms.employeecompetency.graphql;

import com.hrms.employeemanagement.paging.Pagination;
import com.hrms.performancemanagement.model.EmployeePerformance;
import org.springframework.data.domain.Page;

public record EmployeePerformancePagination(Page<EmployeePerformance> data, Pagination pagination) {
}
