package com.hrms.usermanagement.dto;

import lombok.Data;

@Data
public class ChangePassWordDto {
    private String oldPassword;
    private String confirmPassword;
    private String newPassword;
}
