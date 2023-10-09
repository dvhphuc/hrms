package com.hrms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.bind.annotation.CrossOrigin;

@ComponentScans(
        @ComponentScan({"com.hrms.employeemanagement.*"
        }))
@EntityScan({"com.hrms.usermanagement.model","com.hrms.employeemanagement.models"})
@SpringBootApplication
@CrossOrigin
public class HRMSApplication {
    public static void main(String[] args) {
        SpringApplication.run(HRMSApplication.class, args);
    }
}
