package com.hrms.competencymanagement.dto;

import java.util.List;

public record EmployeeSkillMatrix(SkillMatrixData data, List<EmployeeSkillMatrix> children) {
}
