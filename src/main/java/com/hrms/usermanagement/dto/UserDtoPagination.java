package com.hrms.usermanagement.dto;

import com.hrms.global.paging.Pagination;
import com.hrms.usermanagement.dto.UserDto;
import org.springframework.data.domain.Page;

public record UserDtoPagination(Page<UserDto> data, Pagination pagination, long totalCount) {
}