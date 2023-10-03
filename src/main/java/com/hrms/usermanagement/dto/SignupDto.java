package com.hrms.usermanagement.dto;

import lombok.*;

@Data
public class SignupDto {
    private String username;
    private String password;
    private String email;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String address;
    private String city;
}
