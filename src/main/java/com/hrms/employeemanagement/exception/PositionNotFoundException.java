package com.hrms.employeemanagement.exception;

import javax.security.auth.login.CredentialNotFoundException;

public class PositionNotFoundException extends CredentialNotFoundException {
    public PositionNotFoundException(String exception) {
        super(exception);
    }
}
