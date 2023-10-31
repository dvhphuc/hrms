package com.hrms.competencymanagement.dto;

import com.hrms.employeemanagement.models.Employee;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Setter
@Getter
@AllArgsConstructor
public class EmployeeRating {
    Employee employee;
    Float rating;
}