package com.hrms.usermanagement.security;

import com.hrms.usermanagement.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.security.crypto.password.PasswordEncoder;

class SaltGeneratorTest {

    @Test
    void generateSalt() {
        String salt = SaltGenerator.generateSaltString();
        System.out.println(salt);
    }

    @Test
    void hashPassword() {
        String password = "admin";
        String salt = "cb2637ce64cdd0b3dca820b72ab4fad58d0249008ce74aea777765fbe7c27cc6";
        PasswordEncoder passwordEncoder = new org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder();
        String hashedPassword = passwordEncoder.encode(password + salt);
        System.out.println(hashedPassword);

    }

}