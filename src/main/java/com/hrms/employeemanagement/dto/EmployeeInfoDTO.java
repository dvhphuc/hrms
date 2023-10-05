package com.hrms.employeemanagement.dto;

import com.hrms.employeemanagement.models.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
public class EmployeeInfoDTO {
    int id;
    String firstName;
    String lastName;
    String gender;
    String email;
    String phoneNumber;
    String address;
    String dateOfBirth;
    String profileBio;
    PositionLevel positionLevel;
    Department department;
    List<EmergencyContact> emergencyContacts;
    List<Skill> skills;
    List<Project> projects;
}
