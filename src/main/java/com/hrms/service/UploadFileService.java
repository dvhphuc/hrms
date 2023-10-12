package com.hrms.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface UploadFileService {
    String uploadFile(MultipartFile file, Integer employeeId) throws IOException;
}
