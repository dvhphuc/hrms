package com.hrms.usermanagement.service;

import com.hrms.usermanagement.exception.UserNotFoundException;
import com.hrms.usermanagement.exception.WrongPasswordException;
import com.hrms.usermanagement.model.User;
import com.hrms.usermanagement.repository.UserRepository;
import com.hrms.usermanagement.security.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthenticationService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    public User getUser(String username) {
        return userRepository.findByUsername(username);
    }
    public String login(String username, String password) throws Exception {
        return Optional.ofNullable(userRepository.findByUsername(username))
                .filter(user -> passwordEncoder.matches(password, user.getPassword()))
                .filter(user -> user.getIsEnabled())
                .map(user -> jwtService.generateToken(username))
                .orElseThrow(() -> new Exception("Authentication failed"));
    }
}
