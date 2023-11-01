package com.hrms.careerpathmanagement.dto;

import java.util.List;

public record EmployeeSkillMatrix(SkillMatrixData data, List<EmployeeSkillMatrix> children) {
}
