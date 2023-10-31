package com.hrms.performancemanagement.dto;

import com.hrms.global.paging.Pagination;

import java.util.List;

public record PerformanceRatingScorePaging(List<EmployeePerformanceRatingScore> data, Pagination pagination){
}
