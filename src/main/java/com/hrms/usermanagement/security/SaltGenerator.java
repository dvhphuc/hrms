package com.hrms.usermanagement.security;

import java.security.SecureRandom;
import java.util.stream.Stream;

public class SaltGenerator {
    SecureRandom random = new SecureRandom();
    public static byte[] generateSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[32];
        random.nextBytes(salt);
        return salt;
    }

    public static String generateSaltString() {
        StringBuilder salt = new StringBuilder();
        for (byte b : generateSalt()) {
            salt.append(String.format("%02x", b));
        }
        return salt.toString();
    }

}
