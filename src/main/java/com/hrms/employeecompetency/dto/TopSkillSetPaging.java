package com.hrms.employeecompetency.dto;

import com.hrms.employeemanagement.paging.Pagination;

import java.util.List;

public record TopSkillSetPaging(List<TopHighestSkillSet> data, Pagination pagination) {
}
