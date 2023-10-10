package com.hrms.employeemanagement.controllers;

import com.hrms.employeemanagement.exception.EmployeeNotFoundException;
import com.hrms.employeemanagement.exception.JobLevelNotFoundException;
import com.hrms.employeemanagement.exception.PositionLevelNotFoundException;
import com.hrms.employeemanagement.exception.PositionNotFoundException;
import com.hrms.employeemanagement.models.*;
import com.hrms.employeemanagement.paging.EmployeePaging;
import com.hrms.employeemanagement.paging.Pagination;
import com.hrms.employeemanagement.services.*;
import com.hrms.employeemanagement.services.impl.EmployeeImageData;
import com.hrms.employeemanagement.specifications.*;
import com.unboundid.util.NotNull;
import jakarta.annotation.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
public class GraphQLController {
    EmployeeService employeeService;
    PositionLevelService positionLevelService;
    DepartmentService departmentService;
    PositionService positionService;
    JobLevelService jobLevelService;

    @Autowired
    public GraphQLController(EmployeeService employeeService, PositionLevelService positionLevelService,
                             DepartmentService departmentService, PositionService positionService, JobLevelService jobLevelService) {
        this.employeeService = employeeService;
        this.positionLevelService = positionLevelService;
        this.departmentService = departmentService;
        this.positionService = positionService;
        this.jobLevelService = jobLevelService;
    }

    @Value("${file.upload-dir}")
    private String uploadDir;

    @QueryMapping(name = "employees")
    public EmployeePaging findAllEmployees(@Argument int pageNo, @Argument int pageSize,
                                           @Nullable @Argument List<Integer> departmentIds,
                                           @Nullable @Argument List<Integer> currentContracts,
                                           @Nullable @Argument Boolean status) {
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
        Page<Employee> employeeList =
                employeeService.getAllByFilter(departmentIds, currentContracts, status, pageable);
        long totalCount = employeeList.getTotalElements();
        long numberOfPages = (long) Math.ceil(((double) totalCount) / pageSize);
        Pagination pagination = new Pagination(pageNo, pageSize, totalCount, numberOfPages);
        return new EmployeePaging(employeeList.getContent(), pagination, totalCount);
    }

    @QueryMapping(name = "employee")
    public Employee findEmployeeById(@Argument int id) throws EmployeeNotFoundException {
        return employeeService
                .findAll(EmployeeSpecifications.hasId(id))
                .stream()
                .findFirst()
                .orElseThrow(() -> new EmployeeNotFoundException("Employee not found with id: " + id));
    }

    @QueryMapping(name = "employeeOfTheMonth")
    public Iterable<Employee> findNewEmployeeOfMonth() {
        return employeeService.getNewEmployeeOfMonth();
    }

    @QueryMapping
    public EmployeeImageData getEmployeeImage(@Argument int id) throws EmployeeNotFoundException, IOException {
        Employee employee = employeeService
                .findAll(EmployeeSpecifications.hasId(id))
                .stream()
                .findFirst()
                .orElseThrow(() -> new EmployeeNotFoundException("Employee not found with id: " + id));
        String imagePath = employee.getProfilePicture();
        Path filePath = Paths.get(uploadDir, imagePath);
        byte[] imageBytes = Files.readAllBytes(filePath);
        String imageBase64 = Base64.getEncoder().encodeToString(imageBytes);
        String imageUrl = uploadDir + "/" + imagePath;
        return new EmployeeImageData(imageUrl, imageBase64);
    }

    @QueryMapping(name = "departments")
    public List<Department> findAllDepartments() {
        return departmentService.findAll(Specification.allOf());
    }

    @QueryMapping(name = "positions")
    public List<Position> findAllPositions() {
        return positionService.findAll(Specification.allOf());
    }

    @QueryMapping(name = "jobLevels")
    public List<JobLevel> findAllJobLevels() {
        return jobLevelService.findAll(Specification.allOf());
    }

    @MutationMapping
    public Employee createProfile(@Argument String firstName, @Argument String lastName,
                                  @Argument String email, @Argument String gender, @Argument String dateOfBirth,
                                  @Argument String phoneNumber, @Argument String address, @Argument String dateJoined,
                                  @Argument Integer currentContract, @Argument String profileBio,
                                  @Argument String facebookLink, @Argument String twitterLink,
                                  @Argument String linkedinLink, @Argument String instagramLink,
                                  @Argument Integer positionId, @Argument Integer jobLevelId,
                                  @Argument Integer departmentId)
            throws PositionNotFoundException, JobLevelNotFoundException, PositionLevelNotFoundException {
        Employee employee = new Employee();
        return setEmployeeInfo(firstName, lastName, email, gender, dateOfBirth, phoneNumber, address, dateJoined,
                currentContract, profileBio, facebookLink, twitterLink, linkedinLink, instagramLink, positionId,
                jobLevelId, departmentId, employee);
    }

    @MutationMapping
    public Boolean inactiveEmployee(@Argument int id) throws EmployeeNotFoundException {
        Employee employee = employeeService
                .findAll(EmployeeSpecifications.hasId(id))
                .stream()
                .findFirst()
                .orElseThrow(() -> new EmployeeNotFoundException("Employee not found with id: " + id));
        if (Boolean.TRUE.equals(employee.getUser().getIsEnabled())) {
            employee.getUser().setIsEnabled(Boolean.FALSE);
            employeeService.saveEmployee(employee);
            return true;
        }
        return false;
    }

    @MutationMapping
    public Employee updateEmployee(@Argument int id, @Argument String firstName, @Argument String lastName,
                                   @Argument String email, @Argument String gender, @Argument String dateOfBirth,
                                   @Argument String phoneNumber, @Argument String address, @Argument String dateJoined,
                                   @Argument Integer currentContract, @Argument String profileBio,
                                   @Argument String facebookLink, @Argument String twitterLink,
                                   @Argument String linkedinLink, @Argument String instagramLink,
                                   @Argument Integer positionId, @Argument Integer jobLevelId,
                                   @Argument Integer departmentId)
            throws EmployeeNotFoundException, PositionNotFoundException,
            JobLevelNotFoundException, PositionLevelNotFoundException {
        Employee employee = employeeService
                .findAll(EmployeeSpecifications.hasId(id))
                .stream()
                .findFirst()
                .orElseThrow(() -> new EmployeeNotFoundException("Employee not found with id: " + id));
        return setEmployeeInfo(firstName, lastName, email, gender, dateOfBirth, phoneNumber, address, dateJoined,
                currentContract, profileBio, facebookLink, twitterLink, linkedinLink, instagramLink, positionId,
                jobLevelId, departmentId, employee);
    }

    @NotNull
    private Employee setEmployeeInfo(String firstName, String lastName, String email, String gender,
                                     String dateOfBirth, String phoneNumber, String address, String dateJoined,
                                     Integer currentContract, String profileBio, String facebookLink,
                                     String twitterLink, String linkedinLink, String instagramLink,
                                     Integer positionId, Integer jobLevelId, Integer departmentId,
                                     Employee employee)
            throws PositionNotFoundException, JobLevelNotFoundException, PositionLevelNotFoundException {
        employee.setFirstName(firstName);
        employee.setLastName(lastName);
        employee.setEmail(email);
        employee.setGender(gender);
        employee.setDateOfBirth(dateOfBirth);
        employee.setPhoneNumber(phoneNumber);
        employee.setAddress(address);
        employee.setCurrentContract(currentContract);
        employee.setDateJoined(dateJoined);
        employee.setProfileBio(profileBio);
        employee.setFacebookLink(facebookLink);
        employee.setTwitterLink(twitterLink);
        employee.setLinkedinLink(linkedinLink);
        employee.setInstagramLink(instagramLink);
        Position p = positionService
                .findAll(PositionSpecifications.hasId(positionId))
                .stream()
                .findFirst()
                .orElseThrow(() -> new PositionNotFoundException("Position not found with id: " + positionId));
        JobLevel jl = jobLevelService
                .findAll(JobLevelSpecifications.hasId(jobLevelId))
                .stream()
                .findFirst()
                .orElseThrow(() -> new JobLevelNotFoundException("JobLevel not found with id: " + jobLevelId));
        PositionLevel pl = positionLevelService
                .findAll(PositionLevelSpecifications.hasPositionIdAndJobLevelID(p.getId(), jl.getId()))
                .stream()
                .findFirst()
                .orElseThrow(() -> new PositionLevelNotFoundException("PositionLevel not found with positionId: "
                        + p.getId() + " and jobLevelId: " + jl.getId()));
        employee.setPositionLevel(pl);
        Department department = departmentService.findAll(DepartmentSpecifications.hasId(departmentId)).get(0);
        employee.setDepartment(department);
        employeeService.saveEmployee(employee);
        return employee;
    }
}
