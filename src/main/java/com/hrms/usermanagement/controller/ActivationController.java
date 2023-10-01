package com.hrms.usermanagement.controller;

import com.hrms.usermanagement.repository.UserRepository;
import com.hrms.usermanagement.service.ActivationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/active")
public class ActivationController {
    @Autowired
    private ActivationService activationService;

    @PostMapping("/{username}")
    public ResponseEntity<String> activate(@PathVariable String username) {
        activationService.activate(username);
        return ResponseEntity.ok().body("Account activated");
    }

    @PostMapping("/disable/{username}")
    public ResponseEntity<String> disable(@PathVariable String username) {
        activationService.disable(username);
        return ResponseEntity.ok().body("Account disabled");
    }

}
