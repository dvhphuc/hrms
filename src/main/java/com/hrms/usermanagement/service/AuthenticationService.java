package com.hrms.usermanagement.service;

import com.hrms.employeemanagement.models.User;
import com.hrms.usermanagement.exception.UserNotFoundException;
import com.hrms.usermanagement.exception.WrongPasswordException;
import com.hrms.usermanagement.repository.UserRepository;
import com.hrms.usermanagement.security.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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
    public String login(String username, String password) throws UserNotFoundException, WrongPasswordException { //3 values, throw Exception, password + salt
        var user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UserNotFoundException("User not found");
        }

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new WrongPasswordException("Wrong password");
        }

        if (!user.getIsEnabled()) {
            throw new WrongPasswordException("User is not active");
        }

        return jwtService.generateToken(username);
    }
}
