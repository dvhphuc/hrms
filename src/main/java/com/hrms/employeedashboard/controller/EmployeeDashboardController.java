package com.hrms.employeedashboard.controller;

import com.hrms.employeecompetency.models.PositionLevel;
import com.hrms.employeemanagement.models.Employee;
import com.hrms.employeemanagement.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.awt.*;
import java.util.Iterator;

@Controller
public class EmployeeDashboardController {
    @Autowired
    EmployeeService employeeService;

    @QueryMapping
    public Employee employeeOverview(Integer id) {
        return employeeService.getEmployee(id);
    }

//    @QueryMapping
//    public Iterator<PositionLevel> careerPath(Integer id) {
//        return employeeService.getEmployee(id).iterator();;
//    }
}
