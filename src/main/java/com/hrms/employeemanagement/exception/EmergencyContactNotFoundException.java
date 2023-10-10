package com.hrms.employeemanagement.exception;

import javax.security.auth.login.CredentialNotFoundException;

public class EmergencyContactNotFoundException extends CredentialNotFoundException {
    public EmergencyContactNotFoundException(String exception) {
        super(exception);
    }
}
