package com.hrms.employeemanagement.controllers;

import com.hrms.damservice.models.SourceFile;
import com.hrms.employeemanagement.exception.*;
import com.hrms.employeemanagement.dto.*;
import com.hrms.employeemanagement.models.*;
import com.hrms.employeemanagement.paging.EmployeePaging;
import com.hrms.employeemanagement.paging.Pagination;
import com.hrms.employeemanagement.services.*;
import com.hrms.employeemanagement.specifications.*;
import com.hrms.damservice.exception.SourceFileNotFoundException;
import com.hrms.damservice.services.SourceFileService;
import com.hrms.damservice.specifications.SourceFileSpecifications;
import com.unboundid.util.NotNull;
import jakarta.annotation.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
public class EmployeeGraphql {
    EmployeeService employeeService;
    PositionLevelService positionLevelService;
    DepartmentService departmentService;
    EmergencyContactService emergencyContactService;
    SourceFileService sourceFileService;

    @Value("${file.upload-dir}")
    private String uploadDir;

    @Autowired
    public EmployeeGraphql(EmployeeService employeeService, PositionLevelService positionLevelService,
                           DepartmentService departmentService, EmergencyContactService emergencyContactService,
                           SourceFileService sourceFileService) {
        this.employeeService = employeeService;
        this.positionLevelService = positionLevelService;
        this.departmentService = departmentService;
        this.emergencyContactService = emergencyContactService;
        this.sourceFileService = sourceFileService;
    }

    @QueryMapping(name = "employees")
    public EmployeePaging findAllEmployees(@Argument int pageNo, @Argument int pageSize,
                                           @Nullable @Argument List<Integer> departmentIds,
                                           @Nullable @Argument List<Integer> currentContracts,
                                           @Nullable @Argument Boolean status, @Nullable @Argument String name) {
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
        Page<Employee> employeeList =
                employeeService.getAllByFilter(departmentIds, currentContracts, status, name, pageable);
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
    public List<Employee> findNewEmployeeOfMonth() {
        return employeeService.getNewEmployeeOfMonth();
    }

    @QueryMapping(name = "currentHeadcounts")
    public CurrentHeadcount getCurrentHeadcounts() {
        return employeeService.getCurrentHeadcounts();
    }

    @MutationMapping
    public Employee createProfile(@Argument EmployeeInput input)
            throws PositionLevelNotFoundException, EmergencyContactNotFoundException {
        Employee employee = new Employee();
        return setEmployeeInfo(input, employee);
    }

    @MutationMapping
    public Employee updateEmployee(@Argument EmployeeInput input)
            throws EmployeeNotFoundException, PositionLevelNotFoundException,
            EmergencyContactNotFoundException {
        Employee employee = employeeService
                .findAll(EmployeeSpecifications.hasId(input.getId()))
                .stream()
                .findFirst()
                .orElseThrow(() -> new EmployeeNotFoundException("Employee not found with id: " + input.getId()));
        return setEmployeeInfo(input, employee);
    }

    @NotNull
    private Employee setEmployeeInfo(EmployeeInput input, Employee employee)
            throws PositionLevelNotFoundException, EmergencyContactNotFoundException {
        employee.setFirstName(input.getFirstName());
        employee.setLastName(input.getLastName());
        employee.setGender(input.getGender());
        DateTimeFormatter formatter = DateTimeFormatter.ISO_INSTANT;
        Instant instant = Instant.from(formatter.parse(input.getDateOfBirth()));
        Date date = Date.from(instant);
        employee.setDateOfBirth(date);
        employee.setPhoneNumber(input.getPhoneNumber());
        employee.setAddress(input.getAddress());
        employee.setCurrentContract(input.getCurrentContract());
        employee.setProfileBio(input.getProfileBio());
        employee.setFacebookLink(input.getFacebookLink());
        employee.setTwitterLink(input.getTwitterLink());
        employee.setLinkedinLink(input.getLinkedinLink());
        employee.setInstagramLink(input.getInstagramLink());
        PositionLevel pl = positionLevelService
                .findAll(PositionLevelSpecifications.hasPositionIdAndJobLevelID(input.getPositionId(), input.getJobLevelId()))
                .stream()
                .findFirst()
                .orElseThrow(() -> new PositionLevelNotFoundException("PositionLevel not found with positionId: "
                        + input.getPositionId() + " and jobLevelId: " + input.getJobLevelId()));
        employee.setPositionLevel(pl);
        Department department = departmentService.
                findAll(DepartmentSpecifications.hasId(input.getDepartmentId()))
                .stream()
                .findFirst()
                .orElse(null);
        employee.setDepartment(department);
        manageEmergencyContacts(input, employee);
        employeeService.saveEmployee(employee);
        return employee;
    }

    private void manageEmergencyContacts(EmployeeInput input, Employee employee) throws EmergencyContactNotFoundException {
        for (EmergencyContactInput emergencyContactInput : input.getEmergencyContacts()) {
            EmergencyContact emergencyContact;
            if (emergencyContactInput.getId() == null) {
                emergencyContact = new EmergencyContact();
            } else {
                emergencyContact = emergencyContactService
                        .findAll(EmergencyContactSpecifications.hasId(emergencyContactInput.getId()))
                        .stream()
                        .findFirst()
                        .orElseThrow(() ->
                                new EmergencyContactNotFoundException("EmergencyContact not found with id: "
                                        + emergencyContactInput.getId()));
            }
            emergencyContact.setFirstName(emergencyContactInput.getFirstName());
            emergencyContact.setLastName(emergencyContactInput.getLastName());
            emergencyContact.setPhoneNumber(emergencyContactInput.getPhoneNumber());
            emergencyContact.setEmployee(employee);
            emergencyContactService.save(emergencyContact);
        }
    }
}
