package com.hrms.imagemanagement.exception;

import javax.security.auth.login.CredentialNotFoundException;

public class ImageSourceNotFoundException extends CredentialNotFoundException {
    public ImageSourceNotFoundException(String exception) {
        super(exception);
    }
}
