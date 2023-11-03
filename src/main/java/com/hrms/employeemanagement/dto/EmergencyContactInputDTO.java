package com.hrms.employeemanagement.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmergencyContactInputDTO {
    private Integer id;
    private String firstName;
    private String lastName;
    private String phoneNumber;
}
