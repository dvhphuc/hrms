package com.hrms.competencymanagement.graphql;

import com.hrms.global.paging.Pagination;
import com.hrms.performancemanagement.model.EmployeePerformance;
import org.springframework.data.domain.Page;

public record EmployeePerformancePagination(Page<EmployeePerformance> data, Pagination pagination) {
}
