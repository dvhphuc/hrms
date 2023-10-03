package com.hrms.usermanagement.service;

import com.hrms.usermanagement.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

@Service
public class ActivationService {
    @Autowired
    private UserRepository userRepository;

    public void activate(String username) {
        var user = userRepository.findByUsername(username);
        user.setIsEnabled(true);
        userRepository.save(user);
    }

    public void disable(String username) {
        var user = userRepository.findByUsername(username);
        user.setIsEnabled(false);
        userRepository.save(user);
    }
}
