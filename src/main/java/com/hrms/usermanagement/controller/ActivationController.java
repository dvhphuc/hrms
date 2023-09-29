package com.hrms.usermanagement.controller;

import com.hrms.usermanagement.service.ActivationService;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

@Controller
public class ActivationController {
    private ActivationService activationService;
    @PutMapping("/activate/{id}/{status}")
    public String activateUser(@PathVariable String id, @PathVariable boolean status) {
        activationService.activateUser(id, status);
        return "redirect:/home";
    }
}
