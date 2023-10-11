package com.hrms.employeemanagement.services.impl;

import com.hrms.employeemanagement.models.Employee;
import com.hrms.employeemanagement.services.EmployeeService;
import com.hrms.employeemanagement.services.FileStorageService;
import com.hrms.employeemanagement.specifications.EmployeeSpecifications;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class FileStorageServiceImpl implements FileStorageService {
    @Value("${file.upload-dir}")
    private String uploadDir;

    EmployeeService employeeService;
    @Override
    public String storeFile(int employeeID, MultipartFile file) {
        try {
            String fileName = employeeID + "_" + file.getOriginalFilename();
            Path filePath = Paths.get(uploadDir, fileName);
            if (!Files.exists(filePath.getParent())) Files.createDirectories(filePath.getParent());
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            Employee employee = employeeService.findAll(EmployeeSpecifications.hasId(employeeID)).get(0);
            employee.setProfilePicture(fileName);
            employeeService.saveEmployee(employee);
            return "File uploaded successfully.";
        } catch (Exception e) {
            return "Could not upload the file." + e.getMessage();
        }
    }

    @Override
    public byte[] loadFile(String fileName) {
        try {
            Path filePath = Paths.get(uploadDir, fileName);
            File file = new File(filePath.toUri());
            if (file.exists()) {
                byte[] fileData = new byte[(int) file.length()];
                try (FileInputStream fis = new FileInputStream(file)) {
                    fis.read(fileData);
                }
                return fileData;
            } else {
                throw new RuntimeException("File not found: " + fileName);
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to load the file: " + e.getMessage());
        }
    }
}
