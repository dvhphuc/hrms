package com.hrms.usermanagement.security;

import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.stereotype.Component;

@Component
public class JwtService {
    private final String JwtSecret = "secret";

    private final int JwtExpiration = 86400;

    public String generateToken(String username) {
        Algorithm algorithm = Algorithm.HMAC256(JwtSecret);
        return com.auth0.jwt.JWT.create()
                .withClaim("username", username)
                .sign(algorithm);
    }

    public String getUsername(String token) {
        return com.auth0.jwt.JWT.decode(token).getClaim("username").asString();
    }

    public boolean validateToken(String token) {
        try {
            com.auth0.jwt.JWT.decode(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isExpired(String token) {
        try {
            com.auth0.jwt.JWT.decode(token).getExpiresAt();
            return false;
        } catch (Exception e) {
            return true;
        }
    }
}
