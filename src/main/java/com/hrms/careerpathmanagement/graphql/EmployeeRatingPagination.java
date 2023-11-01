package com.hrms.careerpathmanagement.graphql;

import com.hrms.careerpathmanagement.dto.EmployeeRating;
import com.hrms.global.paging.Pagination;
import org.springframework.data.domain.Page;

public record EmployeeRatingPagination(Page<EmployeeRating> data, Pagination pagination) {

}
