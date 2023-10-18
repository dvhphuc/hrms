package com.hrms.employeecompetency.graphql;

import com.hrms.employeecompetency.dto.EmployeeRating;
import com.hrms.employeemanagement.paging.Pagination;
import org.springframework.data.domain.Page;

public record EmployeeRatingPagination(Page<EmployeeRating> data, Pagination pagination) {

}
