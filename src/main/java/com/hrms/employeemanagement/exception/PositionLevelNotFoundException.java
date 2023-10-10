package com.hrms.employeemanagement.exception;

import javax.security.auth.login.CredentialNotFoundException;

public class PositionLevelNotFoundException extends CredentialNotFoundException {
    public PositionLevelNotFoundException(String exception) {
        super(exception);
    }
}
