package com.hrms.employeecompetency.dto;

import com.hrms.employeemanagement.models.Department;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DepartmentInComplete {
    private Department department;
    private Float employeePercentage;
    private Float evaluatorPercentage;
}
