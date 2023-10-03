package com.hrms.usermanagement.controller;

import com.hrms.usermanagement.dto.UpdateDto;
import com.hrms.usermanagement.dto.UserDto;
import com.hrms.usermanagement.model.User;
import com.hrms.usermanagement.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public List<UserDto> getUsers(@RequestParam int pageNo, @RequestParam int pageSize) {
        return userService.getAll(pageNo, pageSize);
    }

    @PutMapping("/update/{username}")
    public ResponseEntity updateUser(@PathVariable String username,
                                     @RequestParam(name = "role") String roleName,
                                     @RequestParam(name = "isEnable") boolean isEnable)
    {
        userService.updateUser(username, roleName, isEnable);
        return ResponseEntity.ok().build();
    }
}
