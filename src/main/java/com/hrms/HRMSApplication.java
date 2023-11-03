package com.hrms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.web.bind.annotation.CrossOrigin;

@ComponentScans(
        @ComponentScan({"com.hrms.employeemanagement.*",
                "com.hrms.usermanagement.*",
                "com.hrms.careerpathmanagement.*",
                "com.hrms.damservice.*",
                "com.hrms.performancemanagement.*",
                "com.hrms.global.*"
        }))
@EntityScan({"com.hrms.usermanagement.model",
        "com.hrms.employeemanagement.models",
        "com.hrms.competencymanagement.models",
        "com.hrms.careerpathmanagement.models",
        "com.hrms.damservice.models",
        "com.hrms.performancemanagement.model"
})
//Test git commit
@SpringBootApplication
@CrossOrigin
public class HRMSApplication {
    public static void main(String[] args) {
        SpringApplication.run(HRMSApplication.class, args);
    }
}
