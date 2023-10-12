package com.hrms.employeedashboard.controller;

import com.hrms.employeemanagement.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.awt.*;
import java.util.Iterator;

@Controller
public class EmployeeDashboardController {
    @Autowired
    EmployeeService employeeService;


}
