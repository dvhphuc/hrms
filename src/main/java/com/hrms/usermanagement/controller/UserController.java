package com.hrms.usermanagement.controller;

import com.hrms.employeemanagement.models.Role;
import com.hrms.employeemanagement.models.User;
import com.hrms.employeemanagement.paging.Pagination;
import com.hrms.usermanagement.dto.SignupDto;
import com.hrms.usermanagement.dto.UserDto;
import com.hrms.usermanagement.exception.UserExistException;
import com.hrms.usermanagement.graphql.UserDtoConnection;
import com.hrms.usermanagement.service.UserService;
import jakarta.annotation.Nullable;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class UserController {
    @Autowired
    private UserService userService;

    private ModelMapper userMapper;

    @Bean
    void initMapper() {
        userMapper = new ModelMapper();
        userMapper.typeMap(User.class, UserDto.class).addMappings(
                mapper -> {
                    mapper.map(User::getUserId, UserDto::setUserId);
                    mapper.map(User::getUsername, UserDto::setUsername);
                    mapper.map(User::getIsEnabled, UserDto::setStatus);
                    mapper.map(User::getCreatedAt, UserDto::setCreatedAt);
                    mapper.map(User::getRoles, UserDto::setRoles);
                }
        );
    }


    @QueryMapping
    public UserDto user(@Argument Integer id) {
        return userService.getUser(id);
    }

    @QueryMapping
    public UserDtoConnection users(@Nullable @Argument String search,
                                   @Nullable @Argument List<Integer> roles,
                                   @Nullable @Argument Boolean status,
                                           @Argument int pageNo,
                                           @Argument int pageSize)
    {
        var sortedByCreatedAtDesc = PageRequest.of(
                pageNo - 1,
                pageSize,
                Sort.by("createdAt").descending()
        );
        if (roles.isEmpty()) roles = List.of(1, 2, 3);
        var users = userService.getAllByFilter(search, roles, status, sortedByCreatedAtDesc);
        var pagination = new Pagination(pageNo, pageSize, users.getTotalElements(), users.getTotalPages());
        return new UserDtoConnection(users.map(u -> userMapper.map(u, UserDto.class)), pagination, users.getTotalElements());
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
            throws IllegalArgumentException
    {
        return userService.updateUsernamePassword(userId, username, password);
    }

    @QueryMapping
    public List<Role> roles() {
        return userService.getRoles();
    }

}
