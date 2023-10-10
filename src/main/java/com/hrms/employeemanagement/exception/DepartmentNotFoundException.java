package com.hrms.employeemanagement.exception;

import javax.security.auth.login.CredentialNotFoundException;

public class DepartmentNotFoundException extends CredentialNotFoundException {
    public DepartmentNotFoundException(String exception) {
        super(exception);
    }
}
