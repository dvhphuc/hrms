package com.hrms.competencymanagement.dto;

import com.hrms.competencymanagement.models.ProficiencyLevel;
import com.hrms.employeemanagement.models.Employee;
import com.hrms.employeemanagement.models.SkillSet;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TopHighestSkillSet {
    private Employee employee;
    private SkillSet skillSet;
    private ProficiencyLevel proficiencyLevel;
}
