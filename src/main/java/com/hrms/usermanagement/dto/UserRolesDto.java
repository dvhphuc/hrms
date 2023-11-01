package com.hrms.usermanagement.dto;

import com.hrms.usermanagement.model.Role;
import lombok.Data;

import java.util.List;

@Data
public class UserRolesDto {
    UserDto userDto;
    List<Role> roles;
}
