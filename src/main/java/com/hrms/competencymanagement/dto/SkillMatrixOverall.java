package com.hrms.competencymanagement.dto;

import lombok.Builder;

@Builder
public record SkillMatrixOverall(String managerName, String status) {
}
