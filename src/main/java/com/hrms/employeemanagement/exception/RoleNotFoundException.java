package com.hrms.employeemanagement.exception;

public class RoleNotFoundException extends RuntimeException {
    public RoleNotFoundException(String exception) {
        super(exception);
    }
}
