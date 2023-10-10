package com.hrms.usermanagement.graphql;

import com.hrms.employeemanagement.models.User;
import com.hrms.usermanagement.dto.UserDto;

public record Token(User user, String token) {
}
