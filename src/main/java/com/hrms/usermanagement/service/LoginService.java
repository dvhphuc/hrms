package com.hrms.usermanagement.service;

import com.hrms.usermanagement.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginService {
    @Autowired
    private UserRepository userRepository;

    public boolean login(String username, String password) {
        return userRepository.findByUsername(username) != null
                && userRepository.findByUsername(username).getPassword().equals(password);
    }
}
