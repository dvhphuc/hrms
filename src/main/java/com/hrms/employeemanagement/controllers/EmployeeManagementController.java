package com.hrms.employeemanagement.controllers;

import com.hrms.employeemanagement.dto.*;
import com.hrms.employeemanagement.models.*;
import com.hrms.employeemanagement.dto.EmployeePagingDTO;
import com.hrms.global.paging.PagingInfo;
import com.hrms.employeemanagement.repositories.DepartmentRepository;
import com.hrms.employeemanagement.repositories.JobLevelRepository;
import com.hrms.employeemanagement.repositories.PositionRepository;
import com.hrms.employeemanagement.services.*;
import com.hrms.damservice.DamService;
import jakarta.annotation.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
//Controller
public class EmployeeManagementController {
    @Autowired
    EmployeeManagementService employeeManagementService;
    @Autowired
    DamService damService;
    @Autowired
    DepartmentRepository departmentRepository;
    @Autowired
    JobLevelRepository jobLevelRepository;
    @Autowired
    PositionRepository positionRepository;

    @Value("${file.upload-dir}")
    private String uploadDir;

    @QueryMapping(name = "employees")
    public EmployeePagingDTO findAllEmployees(@Nullable @Argument List<Integer> departmentIds,
                                              @Nullable @Argument List<Integer> currentContracts,
                                              @Nullable @Argument Boolean status, @Nullable @Argument String name,
                                              @Argument PagingInfo pagingInfo) {
        return employeeManagementService.filterEmployees(departmentIds, currentContracts, status, name, pagingInfo);
    }

    @QueryMapping(name = "employee")
    public EmployeeDetailDTO findEmployeeById(@Argument int id) {
        return employeeManagementService.getEmployeeDetail(id);
    }

    @QueryMapping(name = "newEmployees")
    public List<Employee> findNewEmployees() {
        return employeeManagementService.getNewEmployees();
    }

    @QueryMapping(name = "currentHeadcounts")
    public HeadcountDTO getHeadcounts() {
        return employeeManagementService.getHeadcountsStatistic();
    }

    @QueryMapping(name = "headcountChart")
    public List<HeadcountChartDataDTO> getHeadcountChart() {
        return employeeManagementService.getHeadcountChartData();
    }

    @MutationMapping
    public Employee createProfile(@Argument EmployeeDTO input) throws Exception {
        return employeeManagementService.createEmployee(input);
    }

    @MutationMapping
    public Employee updateEmployee(@Argument EmployeeDTO input) {
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

    @PostMapping("/dam/upload/{employeeId}")
    public ResponseEntity<String> uploadEmployeeProfilePicture(@PathVariable Integer employeeId,
                                                               @RequestParam("file") MultipartFile file,
                                                               @RequestParam("type") String type) {
        try {
            employeeManagementService.uploadFile(file, employeeId, type);
            return ResponseEntity.ok("Profile picture uploaded for Employee ID: " + employeeId);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to upload profile picture.");
        }
    }

    @GetMapping("/dam/retrieve/{employeeId}")
    public ResponseEntity<String> getEmployeeProfilePictureUrl(@PathVariable Integer employeeId) {
        try {
            String url = employeeManagementService.getEmployeeProfilePictureUrl(employeeId);
            return ResponseEntity.ok(url);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
