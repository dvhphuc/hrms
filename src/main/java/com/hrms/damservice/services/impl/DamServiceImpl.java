package com.hrms.damservice.services.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.hrms.damservice.services.DamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;

@Service
@Transactional
public class DamServiceImpl implements DamService {
    @Autowired
    private Cloudinary cloudinary; // Inject Cloudinary instance

    public String uploadFile(MultipartFile file) throws IOException {
        // Get the original file name
        String originalFileName = file.getOriginalFilename();

        // Append a unique identifier to the original file name
        String uniqueFileName = generateUniqueFileName(originalFileName);

        // Upload the file to Cloudinary with the unique file name
        Map uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.asMap("public_id", uniqueFileName));

        // Return the public ID of the uploaded file
        return uploadResult.get("public_id").toString();
    }

    public String getFileUrl(String publicId) {
        // Generate the URL for the file using its public ID
        return cloudinary.url().generate(publicId);
    }

    private String generateUniqueFileName(String fileName) {
        // Append a unique identifier (UUID) to the original file name
        return UUID.randomUUID() + "_" + fileName;
    }
}
