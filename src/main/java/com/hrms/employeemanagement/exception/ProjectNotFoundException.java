package com.hrms.employeemanagement.exception;

import javax.security.auth.login.CredentialNotFoundException;

public class ProjectNotFoundException extends CredentialNotFoundException {
    public ProjectNotFoundException(String exception) {
        super(exception);
    }
}
