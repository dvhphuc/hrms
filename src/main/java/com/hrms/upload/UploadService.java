package com.hrms.upload;

import jakarta.servlet.http.Part;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UploadService {
    public Boolean uploadFile(Part file) {
        log.info("File name: {}", file.getSubmittedFileName());
        return true;
    }
}
