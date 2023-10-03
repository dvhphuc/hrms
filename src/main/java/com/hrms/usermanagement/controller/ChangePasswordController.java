package com.hrms.usermanagement.controller;

import com.hrms.usermanagement.dto.ChangePassWordDto;
import com.hrms.usermanagement.service.ChangePasswordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController("/changePassword")
public class ChangePasswordController {
    @Autowired
    ChangePasswordService changePassWordService;

    @PutMapping("/{username}")
    public void changePassword(
            @PathVariable String username,
            @RequestBody ChangePassWordDto changePassWordDto
    ) throws Exception {
        changePassWordService.changePassword(username, changePassWordDto);
    }
}
