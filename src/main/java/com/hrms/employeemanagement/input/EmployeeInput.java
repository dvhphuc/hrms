package com.hrms.employeemanagement.input;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeInput {
    private String firstName;
    private String lastName;
    private String email;
    private String gender;
    private String dateOfBirth;
    private String phoneNumber;
    private String address;
    private String dateJoined;
    private Integer currentContract;
    private String profileBio;
    private String facebookLink;
    private String twitterLink;
    private String linkedinLink;
    private String instagramLink;
    private Integer positionId;
    private Integer jobLevelId;
    private Integer departmentId;
    private List<EmergencyContactInput> emergencyContacts;
}
