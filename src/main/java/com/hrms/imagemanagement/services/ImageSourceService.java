package com.hrms.imagemanagement.services;

import com.hrms.employeemanagement.exception.EmployeeNotFoundException;
import com.hrms.imagemanagement.exception.ImageSourceNotFoundException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ImageSourceService {
    void uploadProfileImage(int id, MultipartFile file) throws IOException, EmployeeNotFoundException, ImageSourceNotFoundException;
}
