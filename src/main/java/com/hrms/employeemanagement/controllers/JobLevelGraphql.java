package com.hrms.employeemanagement.controllers;

import com.hrms.employeemanagement.models.JobLevel;
import com.hrms.employeemanagement.services.JobLevelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
public class JobLevelGraphql {
    JobLevelService jobLevelService;
    @Autowired
    public JobLevelGraphql(JobLevelService jobLevelService) {
        this.jobLevelService = jobLevelService;
    }

    @QueryMapping(name = "jobLevels")
    public List<JobLevel> findAllJobLevels() {
        return jobLevelService.findAll(Specification.allOf());
    }
}
