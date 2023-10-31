package com.hrms.competencymanagement.dto;

import com.hrms.global.paging.Pagination;

import java.util.List;

public record TopSkillSetPaging(List<TopHighestSkillSet> data, Pagination pagination) {
}
