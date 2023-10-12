package com.hrms.imagemanagement.services;

import com.hrms.employeemanagement.exception.EmergencyContactNotFoundException;
import com.hrms.employeemanagement.exception.EmployeeNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface UploadImageService {
    void uploadProjectImage(int id, MultipartFile file) throws IOException,
            EmergencyContactNotFoundException, EmployeeNotFoundException;
}
