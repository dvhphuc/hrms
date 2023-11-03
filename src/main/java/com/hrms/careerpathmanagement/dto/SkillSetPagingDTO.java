package com.hrms.careerpathmanagement.dto;

import com.hrms.global.paging.Pagination;

import java.util.List;

public record SkillSetPagingDTO(List<SkillSetDTO> data, Pagination pagination) {
}
