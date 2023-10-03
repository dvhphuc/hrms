package com.hrms.usermanagement.security;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class PasswordEncoderTest {
    @Test
    void testHashingPassword() {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        var password = "abc123";

        var passwordHash1 = passwordEncoder.encode(password);

        var passwordHash2 = passwordEncoder.encode(password);

        assert !passwordHash1.equals(passwordHash2);
    }

    @Test
    void testCheckPasswordMatching() {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        var password = "abc123";

        var passwordHash1 = passwordEncoder.encode(password);

        var passwordHash2 = passwordEncoder.encode(password);

        assert passwordEncoder.matches("abc123", passwordHash1);
        assert passwordEncoder.matches("abc123", passwordHash2);
    }
}
