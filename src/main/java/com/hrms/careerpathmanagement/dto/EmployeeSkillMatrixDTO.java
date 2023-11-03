package com.hrms.careerpathmanagement.dto;

import java.util.List;

public record EmployeeSkillMatrixDTO(SkillMatrixDataDTO data, List<EmployeeSkillMatrixDTO> children) {
}
