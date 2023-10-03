package com.hrms.usermanagement.service;

import com.hrms.usermanagement.model.User;
import com.hrms.usermanagement.repository.UserRepository;
import com.hrms.usermanagement.security.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.security.auth.login.CredentialNotFoundException;

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
    public String login(String username, String password) throws Exception { //3 values, throw Exception, password + salt
        var user = userRepository.findByUsername(username);
        if (user == null) {
            throw new CredentialNotFoundException("User not found");
        }

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new CredentialNotFoundException("Wrong password");
        }

        return jwtService.generateToken(username);
    }
}
