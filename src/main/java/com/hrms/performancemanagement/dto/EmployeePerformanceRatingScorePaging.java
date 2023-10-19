package com.hrms.performancemanagement.dto;

import com.hrms.employeemanagement.paging.Pagination;

import java.util.List;

public record EmployeePerformanceRatingScorePaging (List<EmployeePerformanceRatingScore> data, Pagination pagination){
}
