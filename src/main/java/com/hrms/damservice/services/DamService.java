package com.hrms.damservice.services;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface DamService {
    public String uploadFile(MultipartFile file) throws IOException;
    public String getFileUrl(String publicId);
}
