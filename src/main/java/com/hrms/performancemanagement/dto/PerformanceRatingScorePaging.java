package com.hrms.performancemanagement.dto;

import com.hrms.employeemanagement.paging.Pagination;

import java.util.List;

public record PerformanceRatingScorePaging(List<EmployeePerformanceRatingScore> data, Pagination pagination){
}
