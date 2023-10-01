package com.hrms.usermanagement.service;

import com.hrms.usermanagement.dto.SignupDto;
import com.hrms.usermanagement.model.User;
import com.hrms.usermanagement.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SignupService {
    @Autowired
    private UserRepository userRepository;

    public void signup(SignupDto signupDto) {
        if (userRepository.findByUsername(signupDto.getUsername()) != null) {
            throw new RuntimeException("Username already exists");
        }
        var user = new User();
        user.setUsername(signupDto.getUsername());
        user.setPassword(signupDto.getPassword());
        user.setEnabled(false);

        userRepository.save(user);
    }
}
