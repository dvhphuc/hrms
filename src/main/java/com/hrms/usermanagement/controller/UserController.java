package com.hrms.usermanagement.controller;

import com.hrms.usermanagement.dto.SignupDto;
import com.hrms.usermanagement.dto.UserDto;
import com.hrms.usermanagement.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class UserController {
    @Autowired
    private UserService userService;

    @QueryMapping
    public UserDto user(@Argument String username) {
        return userService.getUser(username);
    }

    @QueryMapping
    public Page<UserDto> users(@Argument int pageNo, @Argument int pageSize) {
        var sortedByCreatedAtDesc = PageRequest.of(
                pageNo,
                pageSize,
                Sort.by("createdAt").descending()
        );
        return userService.getAll(sortedByCreatedAtDesc);
    }

    @QueryMapping
    public Page<UserDto> filteredUsers(@Argument List<String> roles,
                                       @Argument List<Boolean> status,
                                       @Argument int pageNo,
                                       @Argument int pageSize)
    {
        var sortedByCreatedAtDesc = PageRequest.of(
                pageNo,
                pageSize,
                Sort.by("createdAt").descending()
        );
        return userService.getAllByFilter(roles, status, sortedByCreatedAtDesc);
    }
    @MutationMapping
    public UserDto createUser(@Argument SignupDto signupDto) {
        return userService.createUser(signupDto);
    }

    @MutationMapping
    public UserDto updateUser(@Argument Integer id,
                              @Argument Boolean status,
                              @Argument String role)
    {
        return userService.updateUser(id, status, role);
    }

}
