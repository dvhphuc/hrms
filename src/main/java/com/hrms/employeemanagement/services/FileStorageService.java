package com.hrms.employeemanagement.services;

import org.springframework.web.multipart.MultipartFile;

public interface FileStorageService {
    String storeFile(int employeeID, MultipartFile file);
    byte[] loadFile(String fileName);
}
