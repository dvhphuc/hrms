package com.hrms.usermanagement.controller;

import com.hrms.usermanagement.dto.LoginDto;
import com.hrms.usermanagement.repository.HRISUserRepository;
import com.hrms.usermanagement.repository.RoleRepository;
import com.hrms.usermanagement.service.HRISUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/login")
public class LoginController {
    private HRISUserDetailsService userDetailsService;

    private HRISUserRepository userRepository;

    private RoleRepository roleRepository;


    @PostMapping("/auth")
    public ResponseEntity<String> login(@RequestBody LoginDto loginDto) {
        Authentication authentication = (Authentication) userDetailsService.loadUserByUsername(loginDto.getUsername());
        if (authentication.isAuthenticated()) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.badRequest().build();
    }
}
