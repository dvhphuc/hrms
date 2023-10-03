package com.hrms.employeemanagement.exception;

public class EmployeeNotFoundException extends RuntimeException {
    public EmployeeNotFoundException(String exception) {
        super(exception);
    }
}
