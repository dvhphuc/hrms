package com.hrms.competencymanagement.graphql;

import com.hrms.competencymanagement.dto.EmployeeRating;
import com.hrms.global.paging.Pagination;
import org.springframework.data.domain.Page;

public record EmployeeRatingPagination(Page<EmployeeRating> data, Pagination pagination) {

}
