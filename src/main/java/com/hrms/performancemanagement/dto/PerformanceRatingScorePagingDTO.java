package com.hrms.performancemanagement.dto;

import com.hrms.global.paging.Pagination;

import java.util.List;

public record PerformanceRatingScorePagingDTO(List<EmployeePerformanceRatingScoreDTO> data, Pagination pagination){
}
