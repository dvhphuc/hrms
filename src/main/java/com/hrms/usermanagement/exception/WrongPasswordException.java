package com.hrms.usermanagement.exception;

import javax.security.auth.login.CredentialNotFoundException;

public class WrongPasswordException extends CredentialNotFoundException {
    public WrongPasswordException(String msg) {
        super(msg);
    }
}
