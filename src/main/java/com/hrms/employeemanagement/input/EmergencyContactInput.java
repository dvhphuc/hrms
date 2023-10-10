package com.hrms.employeemanagement.input;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmergencyContactInput {
    private String firstName;
    private String lastName;
    private String phoneNumber;
}
