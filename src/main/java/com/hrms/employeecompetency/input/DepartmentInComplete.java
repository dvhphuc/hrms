package com.hrms.employeecompetency.input;

import com.hrms.employeemanagement.models.Department;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DepartmentInComplete {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Department department;
    private float percentage;
}
