package com.hrms.damservice.controllers;

import com.hrms.employeemanagement.services.EmployeeManagementService;
import com.hrms.damservice.services.SourceFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/dam")
public class DamController {
    EmployeeManagementService employeeManagementService;
    SourceFileService sourceFileService;
    @Autowired
    public DamController(EmployeeManagementService employeeManagementService, SourceFileService sourceFileService) {
        this.employeeManagementService = employeeManagementService;
        this.sourceFileService = sourceFileService;
    }

    @PostMapping("/upload/{id}")
    public ResponseEntity<String> uploadProfileImage(
            @PathVariable(value = "id") int id, @RequestBody MultipartFile file){
        try {
            sourceFileService.uploadProfileImage(id, file);
            return ResponseEntity.ok("File uploaded successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Could not upload the file.");
        }
    }

    @GetMapping("/profile-image/{id}")
    public ResponseEntity<Resource> getProfileImage(@PathVariable(value = "id") int employeeId){
        try {
            Resource resource = sourceFileService.getProfileImageByEmployeeId(employeeId);
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "inline;filename=" + resource.getFilename())
                    .contentType(MediaType.IMAGE_JPEG)  // Adjust the media type as per your image type
                    .body(resource);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}