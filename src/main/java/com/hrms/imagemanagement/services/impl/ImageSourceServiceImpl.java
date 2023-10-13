package com.hrms.imagemanagement.services.impl;

import com.hrms.employeemanagement.exception.EmployeeNotFoundException;
import com.hrms.employeemanagement.models.Employee;
import com.hrms.employeemanagement.services.EmployeeService;
import com.hrms.imagemanagement.exception.ImageSourceNotFoundException;
import com.hrms.imagemanagement.models.ImageSource;
import com.hrms.imagemanagement.repositories.ImageSourceRepository;
import com.hrms.imagemanagement.services.ImageSourceService;
import com.hrms.employeemanagement.specifications.EmployeeSpecifications;
import com.hrms.imagemanagement.specifications.ImageSourceSpecifications;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
public class ImageSourceServiceImpl implements ImageSourceService {
    @Value("${file.upload-dir}")
    private String uploadDir;
    @Value("${static.folder}")
    private String staticFolder;
    @Autowired
    EmployeeService employeeService;
    @Autowired
    ImageSourceRepository imageSourceRepository;

    @Autowired
    public ImageSourceServiceImpl(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @Override
    public void uploadProfileImage(int employeeId, MultipartFile file) throws IOException, EmployeeNotFoundException, ImageSourceNotFoundException {
        String fileName = employeeId + "_" + file.getOriginalFilename();
        Path filePath = Paths.get(uploadDir, fileName);
        if (!Files.exists(filePath.getParent())) Files.createDirectories(filePath.getParent());
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
        ImageSource imageSource = new ImageSource();
        imageSource.setImagePath(staticFolder + fileName);
        imageSourceRepository.save(imageSource);
        Employee employee = employeeService.findAll(EmployeeSpecifications.hasId(employeeId))
                .stream()
                .findFirst()
                .orElseThrow(() ->
                        new EmployeeNotFoundException("Employee not found with id: " + employeeId));
        ImageSource imgSrc = imageSourceRepository.findAll(ImageSourceSpecifications.hasImagePath(staticFolder + fileName))
                .stream()
                .findFirst()
                .orElseThrow(() ->
                        new ImageSourceNotFoundException("Image not found with path: " + staticFolder + fileName));
        employee.setImageSource(imgSrc);
        employeeService.saveEmployee(employee);
    }
}