package com.hrms.usermanagement.graphql;

import com.hrms.employeemanagement.paging.Pagination;
import com.hrms.usermanagement.dto.UserDto;
import org.springframework.data.domain.Page;

public record UserDtoConnection(Page<UserDto> data, Pagination pagination, long totalCount) {
}