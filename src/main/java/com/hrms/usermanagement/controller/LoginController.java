package com.hrms.usermanagement.controller;

import com.hrms.usermanagement.dto.LoginDto;
import com.hrms.usermanagement.repository.HRISUserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/login")
public class LoginController {
    @Autowired
    private HRISUserRepository userRepository;

    @PostMapping("/auth")
    public ResponseEntity<String> login(@RequestBody LoginDto loginDto) {
        if (userRepository.findByUsername(loginDto.getUsername()) == null) {
            return ResponseEntity.status(401).body("Invalid username");
        }
        return ResponseEntity.ok().body("Login successful");
    }
}
