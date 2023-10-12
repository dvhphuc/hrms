package com.hrms.service.impl;

import com.hrms.employeecompetency.models.Qualification;
import com.hrms.employeedashboard.repository.QualificationRepository;
import com.hrms.employeemanagement.repositories.EmployeeRepository;
import com.hrms.service.UploadFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.sql.Date;

@Service
public class UploadFileServiceImpl implements UploadFileService {
    @Value("${file.upload-dir}")
    private String uploadDir;

    @Autowired
    QualificationRepository qualificationRepository;

    @Autowired
    EmployeeRepository employeeRepository;

    @Override
    public String uploadFile(@RequestParam("file") MultipartFile file,
                             Integer employeeId)
            throws IOException
    {
        Path targetLocation = Path.of(uploadDir + file.getOriginalFilename());
        Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
        var qualification = new Qualification();
        qualification.setEmployee(employeeRepository.getById(employeeId));
        qualification.setName(file.getOriginalFilename());
        qualification.setUploadedDate(Date.valueOf(java.time.LocalDate.now()));
        qualificationRepository.save(qualification);
        return "File uploaded successfully.";
    }
}
