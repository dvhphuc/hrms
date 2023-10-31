package com.hrms.competencymanagement.exceptions;

import javax.security.auth.login.CredentialNotFoundException;

public class CompetencyCycleNotFoundException extends CredentialNotFoundException {
    public CompetencyCycleNotFoundException(String exception) {
        super(exception);
    }
}
