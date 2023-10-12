package com.hrms.imagemanagement.controllers;

import com.hrms.employeemanagement.exception.EmployeeNotFoundException;
import com.hrms.employeemanagement.services.EmployeeService;
import com.hrms.imagemanagement.services.UploadImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    EmployeeService employeeService;
    UploadImageService uploadImageService;
    @Autowired
    public EmployeeController(EmployeeService employeeService, UploadImageService uploadImageService) {
        this.employeeService = employeeService;
        this.uploadImageService = uploadImageService;
    }

    @PostMapping("/{id}/upload")
    public ResponseEntity<String> uploadProfileImage(
            @PathVariable(value = "id") int id, @RequestParam("file") MultipartFile file){
        try {
            uploadImageService.uploadProjectImage(id, file);
            return ResponseEntity.ok("File uploaded successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Could not upload the file.");
        }
    }
}