package com.hrms.usermanagement.controller;

import com.hrms.usermanagement.dto.LoginDto;
import com.hrms.usermanagement.exception.UserNotFoundException;
import com.hrms.usermanagement.exception.WrongPasswordException;
import com.hrms.usermanagement.model.User;
import com.hrms.usermanagement.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.security.auth.login.CredentialNotFoundException;

@RestController
@RequestMapping("/login")
public class LoginController {
    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("/auth")
    public String login(
            @RequestHeader("Authentication") String jwtToken,
            @RequestBody LoginDto loginDto)
            throws UserNotFoundException, WrongPasswordException
    {
        return authenticationService.login(loginDto.getUsername(), loginDto.getPassword());
    }

    @GetMapping("/users/{username}")
    public ResponseEntity<User> getUser(@PathVariable String username) {
        var user = authenticationService.getUser(username);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(user);
    }
}
