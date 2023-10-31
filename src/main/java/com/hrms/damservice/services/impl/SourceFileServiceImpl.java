package com.hrms.damservice.services.impl;

import com.hrms.damservice.models.SourceFile;
import com.hrms.employeemanagement.exception.EmployeeNotFoundException;
import com.hrms.employeemanagement.models.Employee;
import com.hrms.employeemanagement.services.EmployeeManagementService;
import com.hrms.damservice.exception.SourceFileNotFoundException;
import com.hrms.damservice.repositories.SourceFileRepository;
import com.hrms.damservice.services.SourceFileService;
import com.hrms.damservice.specifications.SourceFileSpecifications;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

@Service
@Transactional
public class SourceFileServiceImpl implements SourceFileService {
    @Value("${file.upload-dir}")
    private String uploadDir;
    @Value("${static.folder}")
    private String imageApi;
    @Autowired
    EmployeeManagementService employeeManagementService;
    @Autowired
    SourceFileRepository sourceFileRepository;

    @Override
    public void uploadProfileImage(int employeeId, MultipartFile file) throws IOException, EmployeeNotFoundException, SourceFileNotFoundException {
        String fileName = employeeId + "_" + file.getOriginalFilename();
        Path filePath = Paths.get(uploadDir, fileName);
        String imagePath = imageApi + employeeId;
        SourceFile imgSrc = sourceFileRepository.findAll(SourceFileSpecifications.hasImagePath(imagePath))
                .stream()
                .findFirst()
                .orElse(null);
        if(imgSrc != null) {
            Path filePathExist = Paths.get(uploadDir, imgSrc.getFileName());
            Files.delete(filePathExist);
            sourceFileRepository.delete(imgSrc);
        }
        if (!Files.exists(filePath.getParent())) Files.createDirectories(filePath.getParent());
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
        SourceFile sourceFile = new SourceFile();
        sourceFile.setFileName(fileName);
        sourceFile.setFilePath(imagePath);
        sourceFile.setFileType(file.getContentType());
        SourceFile savedSourceFile = sourceFileRepository.save(sourceFile);
        Employee employee = employeeManagementService.findEmployee(employeeId);
        employee.setDamId(savedSourceFile.getId());
    }

    public Resource getProfileImageByEmployeeId(int employeeId) throws SourceFileNotFoundException, EmployeeNotFoundException {
        Employee employee = employeeManagementService.findEmployee(employeeId);
        SourceFile sourceFile = sourceFileRepository.findAll(SourceFileSpecifications.hasId(employee.getDamId()))
                .stream()
                .findFirst()
                .orElseThrow(() ->
                        new SourceFileNotFoundException("Image source not found with employee has id: " + employeeId));
        String imageName = sourceFile.getFileName();
        Path imageFilePath = Paths.get(uploadDir, imageName);
        return new FileSystemResource(imageFilePath.toFile());
    }

    @Override
    public List<SourceFile> findAll(Specification<SourceFile> spec) {
        return sourceFileRepository.findAll(spec);
    }

    @Override
    public List<SourceFile> findAll(Specification<SourceFile> spec, Sort sort) {
        return sourceFileRepository.findAll(spec, sort);
    }

    @Override
    public Page<SourceFile> findAll(Specification<SourceFile> spec, Pageable pageable) {
        return sourceFileRepository.findAll(spec, pageable);
    }
}
