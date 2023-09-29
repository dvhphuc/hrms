package com.hrms.usermanagement.service;

import com.hrms.usermanagement.repository.HRISUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;

public class ActivationService {
    @Autowired
    private HRISUserRepository userRepository;

    @Bean
    public void activateUser(String username, boolean status) {
        var user = userRepository.findByUsername(username);
        user.setEnabled(status);
        userRepository.save(user);
    }
}
