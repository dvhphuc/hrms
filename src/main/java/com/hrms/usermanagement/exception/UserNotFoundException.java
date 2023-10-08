package com.hrms.usermanagement.exception;

import org.springframework.http.HttpStatus;

import javax.security.auth.login.CredentialNotFoundException;

public class UserNotFoundException extends CredentialNotFoundException {
    public UserNotFoundException(String msg) {
        super(msg);
    }
}
