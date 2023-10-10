package com.hrms.employeemanagement.exception;

import javax.security.auth.login.CredentialNotFoundException;

public class EmployeeNotFoundException extends CredentialNotFoundException {
    public EmployeeNotFoundException(String exception) {
        super(exception);
    }
}
