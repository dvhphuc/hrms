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
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
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
    private String imageApi;
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
        String imagePath = imageApi + employeeId + "/image";
        ImageSource imgSrc = imageSourceRepository.findAll(ImageSourceSpecifications.hasImagePath(imagePath))
                .stream()
                .findFirst()
                .orElse(null);
        if(imgSrc != null) {
            Path filePathExist = Paths.get(uploadDir, imgSrc.getImageName());
            Files.delete(filePathExist);
            imageSourceRepository.delete(imgSrc);
        }
        if (!Files.exists(filePath.getParent())) Files.createDirectories(filePath.getParent());
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
        ImageSource imageSource = new ImageSource();
        imageSource.setImageName(fileName);
        imageSource.setImagePath(imagePath);
        ImageSource newImageSource = imageSourceRepository.save(imageSource);
        Employee employee = employeeService.findAll(EmployeeSpecifications.hasId(employeeId))
                .stream()
                .findFirst()
                .orElseThrow(() ->
                        new EmployeeNotFoundException("Employee not found with id: " + employeeId));
        employee.setImageSource(newImageSource);
        employeeService.saveEmployee(employee);
    }

    public Resource getProfileImageByEmployeeId(int employeeId) throws EmployeeNotFoundException {
        Employee employee = employeeService.findAll(EmployeeSpecifications.hasId(employeeId))
                .stream()
                .findFirst()
                .orElseThrow(() ->
                        new EmployeeNotFoundException("Employee not found with id: " + employeeId));

        String imageName = employee.getImageSource().getImageName();
        Path imageFilePath = Paths.get(uploadDir, imageName);
        return new FileSystemResource(imageFilePath.toFile());
    }
}
