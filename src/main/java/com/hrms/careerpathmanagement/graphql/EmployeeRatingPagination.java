package com.hrms.careerpathmanagement.graphql;

import com.hrms.careerpathmanagement.dto.EmployeeRatingDTO;
import com.hrms.global.paging.Pagination;
import org.springframework.data.domain.Page;

public record EmployeeRatingPagination(Page<EmployeeRatingDTO> data, Pagination pagination) {

}
