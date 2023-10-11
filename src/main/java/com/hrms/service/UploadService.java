package com.hrms.service;

import graphql.kickstart.servlet.apollo.ApolloScalars;
import jakarta.servlet.http.Part;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;

@Service
public class UploadService {
    @Value("file.upload-dir")
    private String uploadDir;

    public String upload(Part fileUpload) throws IOException {
        //save file to static resource folder
        var fileName = fileUpload.getSubmittedFileName();
        var file = new FileOutputStream(uploadDir + fileName);
        file.write(fileUpload.getInputStream().readAllBytes());
        return "Uploaded";
    }
}
