package com.hrms.controller;

import com.hrms.service.UploadService;
import graphql.kickstart.servlet.apollo.ApolloScalars;
import jakarta.servlet.http.Part;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;

import java.io.IOException;

public class UploadController {
    @Autowired
    private UploadService uploadService;

    @MutationMapping
    public String upload(@Argument Part file) throws IOException {
        return uploadService.upload(file);
    }

}
