package com.hrms.usermanagement.controller;

import com.hrms.usermanagement.dto.LoginDto;
import com.hrms.usermanagement.dto.SignupDto;
import com.hrms.usermanagement.model.User;
import com.hrms.usermanagement.repository.UserRepository;
import com.hrms.usermanagement.service.SignupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/signup")
public class SignupController {
    @Autowired
    private SignupService signupService;

    @RequestMapping("/")
    public ResponseEntity<String> signup(@RequestBody SignupDto signupDto) {
        signupService.signup(signupDto);
        return ResponseEntity.ok().body("Signup successful");
    }
}
