package com.hrms.employeemanagement.controllers;

import com.hrms.employeemanagement.dto.*;
import com.hrms.employeemanagement.models.*;
import com.hrms.employeemanagement.dto.EmployeePaging;
import com.hrms.global.paging.PagingInfo;
import com.hrms.employeemanagement.repositories.DepartmentRepository;
import com.hrms.employeemanagement.repositories.JobLevelRepository;
import com.hrms.employeemanagement.repositories.PositionRepository;
import com.hrms.employeemanagement.services.*;
import com.hrms.damservice.services.SourceFileService;
import jakarta.annotation.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
//Controller
public class EmployeeManagement {
    EmployeeManagementService employeeManagementService;
    SourceFileService sourceFileService;
    DepartmentRepository departmentRepository;
    JobLevelRepository jobLevelRepository;
    PositionRepository positionRepository;

    @Value("${file.upload-dir}")
    private String uploadDir;

    @Autowired
    public EmployeeManagement(EmployeeManagementService employeeManagementService, SourceFileService sourceFileService) {
        this.employeeManagementService = employeeManagementService;
        this.sourceFileService = sourceFileService;
    }

    @QueryMapping(name = "employees")
    public EmployeePaging findAllEmployees(@Nullable @Argument List<Integer> departmentIds,
                                           @Nullable @Argument List<Integer> currentContracts,
                                           @Nullable @Argument Boolean status, @Nullable @Argument String name,
                                           @Argument PagingInfo pagingInfo) {
        return employeeManagementService.filterEmployees(departmentIds, currentContracts, status, name, pagingInfo);
    }

    @QueryMapping(name = "employee")
    public EmployeeDetail findEmployeeById(@Argument int id) {
        return employeeManagementService.getEmployeeDetail(id);
    }

    @QueryMapping(name = "newEmployees")
    public List<Employee> findNewEmployees() {
        return employeeManagementService.getNewEmployees();
    }

    @QueryMapping(name = "currentHeadcounts")
    public Headcount getHeadcounts() {
        return employeeManagementService.getHeadcountsStatistic();
    }

    @QueryMapping(name = "headcountChart")
    public List<HeadcountChartData> getHeadcountChart() {
        return employeeManagementService.getHeadcountChartData();
    }

    @MutationMapping
    public Employee createProfile(@Argument EmployeeDTO input) throws Exception {
        return employeeManagementService.createEmployee(input);
    }

    @MutationMapping
    public Employee updateEmployee(@Argument EmployeeDTO input) throws Exception {
        return employeeManagementService.updateEmployee(input);
    }

    @QueryMapping(name = "departments")
    public List<Department> getDepartments() {
        return departmentRepository.findAll();
    }

    @QueryMapping(name = "NumberOfDepartments")
    public Long getNumberOfDepartments() {
        return departmentRepository.count();
    }

    @QueryMapping(name = "jobLevels")
    public List<JobLevel> getJobLevels() {
        return jobLevelRepository.findAll();
    }

    @QueryMapping(name = "positions")
    public List<Position> getPositions() {
        return positionRepository.findAll();
    }
}
