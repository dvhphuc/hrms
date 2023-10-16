package com.hrms.employeecompetency.controllers;

import com.hrms.employeecompetency.dto.EmployeeOverviewDto;
import com.hrms.employeecompetency.mapper.EmployeeMapperService;
import com.hrms.employeemanagement.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

@Controller
public class EmployeeDashboardController {
    @Autowired
    EmployeeService employeeService;

    @Autowired
    EmployeeMapperService employeeMapperService;

    @QueryMapping
    public EmployeeOverviewDto employeeOverview(@Argument Integer id) {
        return employeeMapperService.employeeOverview(employeeService.findById(id));
    }

    @QueryMapping
    public CareerPathSummaryDto careerPathSummary(@Argument Integer id) {
        return employeeMapperService.careerPathSummary(employeeService.findById(id));
    }
}
