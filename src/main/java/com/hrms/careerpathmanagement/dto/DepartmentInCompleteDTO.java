package com.hrms.careerpathmanagement.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DepartmentInCompleteDTO {
    private Integer departmentId;
    private float employeePercentage;
    private float evaluatorPercentage;
}
