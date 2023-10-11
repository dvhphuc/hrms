package com.hrms.service.impl;

import com.hrms.employeemanagement.exception.EmployeeNotFoundException;
import com.hrms.employeemanagement.models.Employee;
import com.hrms.employeemanagement.services.EmployeeService;
import com.hrms.service.UploadImageService;
import com.hrms.employeemanagement.specifications.EmployeeSpecifications;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
@Transactional
public class UploadImageServiceImpl implements UploadImageService {
    @Value("${file.upload-dir}")
    private String uploadDir;
    EmployeeService employeeService;

    @Autowired
    public UploadImageServiceImpl(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @Override
    public void uploadProjectImage(int employeeId, MultipartFile file) throws IOException, EmployeeNotFoundException {
        String fileName = employeeId + "_" + file.getOriginalFilename();
        Path filePath = Paths.get(uploadDir, fileName);
        if (!Files.exists(filePath.getParent())) Files.createDirectories(filePath.getParent());
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        Employee employee = employeeService.findAll(EmployeeSpecifications.hasId(employeeId))
                .stream()
                .findFirst()
                .orElseThrow(() ->
                        new EmployeeNotFoundException("Employee not found with id: " + employeeId));
        employee.setProfilePicture(fileName);
        employeeService.saveEmployee(employee);
    }

    @Override
    public ResponseEntity<byte[]> getProductImage(int employeeId) throws IOException, EmployeeNotFoundException {
        Employee employee = employeeService.findAll(EmployeeSpecifications.hasId(employeeId))
                .stream()
                .findFirst()
                .orElseThrow(() ->
                        new EmployeeNotFoundException("Employee not found with id: " + employeeId));
        String imagePath = employee.getProfilePicture();
        Path filePath = Paths.get(uploadDir, imagePath);
        byte[] imageBytes = Files.readAllBytes(filePath);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG); // Set appropriate content type
        headers.setContentDispositionFormData("attachment", imagePath);
        return new ResponseEntity<>(imageBytes, headers, HttpStatus.OK);
    }
}
