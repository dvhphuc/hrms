package com.hrms.upload;

import jakarta.servlet.http.Part;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.stereotype.Controller;

@Controller
public class UploadController {
    @Autowired
    UploadService uploadService;

    @MutationMapping
    public Boolean uploadFile(Part file) {
        return uploadService.uploadFile(file);
    }
}
