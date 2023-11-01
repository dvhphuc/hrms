package com.hrms.damservice.services;

import com.hrms.damservice.models.SourceFile;
import com.hrms.employeemanagement.exception.EmployeeNotFoundException;
import com.hrms.damservice.exception.SourceFileNotFoundException;
import jakarta.annotation.Nullable;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface SourceFileService {
    void uploadProfileImage(int id, MultipartFile file) throws IOException, EmployeeNotFoundException, SourceFileNotFoundException;
    Resource getProfileImageByEmployeeId(int employeeId) throws EmployeeNotFoundException, SourceFileNotFoundException;
    List<SourceFile> getQualifications(Integer employeeId, @Nullable String search, String sort);
}
