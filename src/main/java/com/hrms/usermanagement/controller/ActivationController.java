package com.hrms.usermanagement.controller;

import com.hrms.usermanagement.repository.UserRepository;
import com.hrms.usermanagement.service.ActivationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/active")
public class ActivationController {
    @Autowired
    private ActivationService activationService;

    @PutMapping
    public ResponseEntity<String> activate(@RequestParam String username) {
        activationService.activate(username);
        return ResponseEntity.ok().body("Account activated");
    }

    @PutMapping("/disable")
    public ResponseEntity<String> disable(@RequestParam String username) {
        activationService.disable(username);
        return ResponseEntity.ok().body("Account disabled");
    }

}
