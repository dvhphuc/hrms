package com.hrms.usermanagement.controller;

import com.hrms.usermanagement.dto.UpdateDto;
import com.hrms.usermanagement.dto.UserDto;
import com.hrms.usermanagement.model.User;
import com.hrms.usermanagement.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping
    public List<UserDto> getUsers() {
        return userService.getAll();
    }

    @GetMapping("/page")
    public Page<UserDto> getUsers(@RequestParam int pageNo, @RequestParam int pageSize) {
        return userService.getAll(PageRequest.of(pageNo, pageSize));
    }

    @GetMapping("/filter")
    public Page<UserDto> getUsers(@RequestParam(required = false, value = "roleName") String roleName,
                                  @RequestParam(required = false, value = "status") Boolean status,
                                  @RequestParam int pageNo,
                                  @RequestParam int pageSize) {
        return userService.getAll(roleName, status, PageRequest.of(pageNo, pageSize));
    }

    @PutMapping("/update/{username}")
    public ResponseEntity updateUser(@PathVariable String username,
                                     @RequestParam(name = "roleName") String roleName,
                                     @RequestParam(name = "isEnable") boolean isEnable)
    {
        userService.updateUser(username, roleName, isEnable);
        return ResponseEntity.ok().build();
    }
}
