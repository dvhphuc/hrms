package com.hrms.employeemanagement.exception;

import javax.security.auth.login.CredentialNotFoundException;

public class JobLevelNotFoundException extends CredentialNotFoundException {
    public JobLevelNotFoundException(String exception) {
        super(exception);
    }
}
