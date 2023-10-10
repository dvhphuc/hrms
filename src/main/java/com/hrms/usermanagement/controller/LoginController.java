package com.hrms.usermanagement.controller;

import com.hrms.usermanagement.exception.UserNotFoundException;
import com.hrms.usermanagement.exception.WrongPasswordException;
import com.hrms.usermanagement.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

@Controller
public class LoginController {
    @Autowired
    private AuthenticationService authenticationService;

    @QueryMapping("jwtToken")
    public String login(@Argument String username, @Argument String password)
            throws UserNotFoundException, WrongPasswordException
    {
        return authenticationService.login(username, password);
    }

}
