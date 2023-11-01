package com.hrms.usermanagement.controller;

import com.hrms.global.mapper.HrmsMapper;
import com.hrms.global.paging.Pagination;
import com.hrms.usermanagement.dto.SignupDto;
import com.hrms.usermanagement.dto.UserDto;
import com.hrms.usermanagement.dto.UserDtoPagination;
import com.hrms.usermanagement.model.User;
import com.hrms.usermanagement.service.UserService;
import jakarta.annotation.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private HrmsMapper userMapper;

    @QueryMapping
    public UserDto user(@Argument Integer userId) throws Exception {
        var user = userService.getUser(userId);
        return userMapper.map(user, UserDto.class);
    }

    @QueryMapping
    public UserDtoPagination users(@Nullable @Argument String search,
                                   @Nullable @Argument List<Integer> roleIds,
                                   @Nullable @Argument Boolean status,
                                   @Argument int pageNo,
                                   @Argument int pageSize)
    {
        var pageable = PageRequest.of(pageNo - 1, pageSize, Sort.by("createdAt").descending());
        return userService.searchUsers(search, roleIds, status, pageable);
    }

    @MutationMapping
    public User createUser(@Argument SignupDto signupDto) throws Exception {
        return userService.createUser(signupDto);
    }

    @MutationMapping
    public Boolean updateUsers(@Argument List<Integer> ids,
                               @Argument Boolean status,
                               @Argument List<Integer> roles)
    {
        return userService.updateUsers(ids, status, roles);
    }

    @QueryMapping
    public List<UserDto> getUsers() throws Exception {
        return userService.getUsers(1);
    }

}
