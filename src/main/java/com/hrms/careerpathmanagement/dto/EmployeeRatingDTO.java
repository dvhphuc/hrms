package com.hrms.careerpathmanagement.dto;

import com.hrms.employeemanagement.models.Employee;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Setter
@Getter
@AllArgsConstructor
public class EmployeeRatingDTO {
    Employee employee;
    Float rating;
}