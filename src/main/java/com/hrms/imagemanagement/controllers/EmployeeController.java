package com.hrms.imagemanagement.controllers;

import com.hrms.employeemanagement.services.EmployeeService;
import com.hrms.imagemanagement.services.ImageSourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    EmployeeService employeeService;
    ImageSourceService imageSourceService;
    @Autowired
    public EmployeeController(EmployeeService employeeService, ImageSourceService imageSourceService) {
        this.employeeService = employeeService;
        this.imageSourceService = imageSourceService;
    }

    @PostMapping("/{id}/upload")
    public ResponseEntity<String> uploadProfileImage(
            @PathVariable(value = "id") int id, @RequestBody MultipartFile file){
        try {
            imageSourceService.uploadProfileImage(id, file);
            return ResponseEntity.ok("File uploaded successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Could not upload the file.");
        }
    }
}