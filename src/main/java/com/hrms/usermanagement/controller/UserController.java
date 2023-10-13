package com.hrms.usermanagement.controller;

import com.hrms.employeemanagement.models.Role;
import com.hrms.employeemanagement.paging.Pagination;
import com.hrms.usermanagement.dto.SignupDto;
import com.hrms.usermanagement.dto.UserDto;
import com.hrms.usermanagement.exception.UserExistException;
import com.hrms.usermanagement.exception.UserNotFoundException;
import com.hrms.usermanagement.graphql.UserDtoConnection;
import com.hrms.usermanagement.service.UserService;
import jakarta.annotation.Nullable;
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
    public UserDto user(@Argument Integer id) {
        return userService.getUser(id);
    }

    @QueryMapping
    public UserDtoConnection users(@Nullable @Argument String search,
                                           @Argument List<Integer> roles,
                                           @Argument Boolean status,
                                           @Argument int pageNo,
                                           @Argument int pageSize)
    {
        var sortedByCreatedAtDesc = PageRequest.of(
                pageNo - 1,
                pageSize,
                Sort.by("createdAt").descending()
        );
        var users = userService.getAllByFilter(search, roles, status, sortedByCreatedAtDesc);
        var pagination = new Pagination(pageNo, pageSize, users.getTotalElements(), users.getTotalPages());
        return new UserDtoConnection(users, pagination, users.getTotalElements());
    }

    @MutationMapping
    public Boolean createUser(@Argument SignupDto signupDto) throws UserExistException {
        return userService.createUser(signupDto);
    }

    @MutationMapping
    public Boolean updateUsers(@Argument List<Integer> ids,
                               @Argument Boolean status,
                               @Argument List<Integer> roles)
    {
        return userService.updateUsers(ids, status, roles);
    }

    @MutationMapping
    public Boolean updateUsernamePassword(@Argument Integer userId,
                                          @Argument String username,
                                          @Argument String password)
            throws UserNotFoundException
    {
        return userService.updateUsernamePassword(userId, username, password);
    }

    @QueryMapping
    public List<Role> roles() {
        return userService.getRoles();
    }

}
