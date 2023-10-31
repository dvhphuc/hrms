package com.hrms.competencymanagement.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DepartmentInComplete {
    private Integer departmentId;
    private float employeePercentage;
    private float evaluatorPercentage;
}
