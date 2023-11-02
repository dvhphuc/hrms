package com.hrms.careerpathmanagement.graphql;

import com.hrms.global.paging.Pagination;
import com.hrms.performancemanagement.model.PerformanceEvaluation;
import org.springframework.data.domain.Page;

public record EmployeePerformancePagination(Page<PerformanceEvaluation> data, Pagination pagination) {
}
