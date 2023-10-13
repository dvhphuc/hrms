package com.hrms.employeecompetency.dto;

import com.hrms.employeemanagement.models.PositionLevel;
import com.hrms.employeemanagement.models.Skill;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Data
@Getter
@Setter
public class EmployeeOverviewDto {
    Integer employeeId;
    String firstName;
    String lastName;
    PositionLevel positionLevel;
    String address;
    List<Skill> skills;
    List<Skill> interests;
    String certification;
}
