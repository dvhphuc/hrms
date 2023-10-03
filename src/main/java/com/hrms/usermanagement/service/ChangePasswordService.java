package com.hrms.usermanagement.service;

import com.hrms.usermanagement.dto.ChangePassWordDto;
import com.hrms.usermanagement.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class ChangePasswordService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void changePassword(String username, ChangePassWordDto changePassWordDto) throws Exception {
        var user = userRepository.findByUsername(username);
        var oldPassword = changePassWordDto.getOldPassword();
        var newPassword = changePassWordDto.getNewPassword();

        if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
            throw new Exception("Wrong password");
        }
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }
}
