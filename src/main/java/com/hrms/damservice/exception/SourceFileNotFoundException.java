package com.hrms.damservice.exception;

import javax.security.auth.login.CredentialNotFoundException;

public class SourceFileNotFoundException extends CredentialNotFoundException {
    public SourceFileNotFoundException(String exception) {
        super(exception);
    }
}
