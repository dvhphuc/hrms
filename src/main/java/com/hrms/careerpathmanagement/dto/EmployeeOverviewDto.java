package com.hrms.careerpathmanagement.dto;

import com.hrms.employeemanagement.models.Employee;
import com.hrms.employeemanagement.models.SkillSet;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Data
@Getter
@Setter
public class EmployeeOverviewDto {
    Employee employee;
    String address;
    List<SkillSet> skills;
    List<SkillSet> interests;
    List<String> certification;
}
