package com.hrms.usermanagement.controller;

import com.hrms.usermanagement.dto.LoginDto;
import com.hrms.usermanagement.repository.UserRepository;
import com.hrms.usermanagement.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/login")
public class LoginController {
    @Autowired
    private LoginService loginService;

    @PostMapping("/auth")
    public ResponseEntity<String> login(@RequestBody LoginDto loginDto) {
        var isValid = loginService.login(loginDto.getUsername(), loginDto.getPassword());
        if (!isValid) {
            return ResponseEntity.status(401).body("Invalid username or password");
        }

        return ResponseEntity.ok().body("Login successful");
    }
}
