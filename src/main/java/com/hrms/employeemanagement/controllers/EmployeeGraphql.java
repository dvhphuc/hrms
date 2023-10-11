package com.hrms.employeemanagement.controllers;

import com.hrms.employeecompetency.models.JobLevel;
import com.hrms.employeecompetency.models.Position;
import com.hrms.employeecompetency.models.PositionLevel;
import com.hrms.employeemanagement.exception.*;
import com.hrms.employeemanagement.input.EmergencyContactInput;
import com.hrms.employeemanagement.input.EmployeeInput;
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
public class EmployeeGraphql {
    EmployeeService employeeService;
    PositionLevelService positionLevelService;
    DepartmentService departmentService;
    PositionService positionService;
    JobLevelService jobLevelService;
    EmergencyContactService emergencyContactService;

    @Value("${file.upload-dir}")
    private String uploadDir;

    @Autowired
    public EmployeeGraphql(EmployeeService employeeService, PositionLevelService positionLevelService,
                           DepartmentService departmentService, PositionService positionService,
                           JobLevelService jobLevelService, EmergencyContactService emergencyContactService) {
        this.employeeService = employeeService;
        this.positionLevelService = positionLevelService;
        this.departmentService = departmentService;
        this.positionService = positionService;
        this.jobLevelService = jobLevelService;
        this.emergencyContactService = emergencyContactService;
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
    public Employee createProfile(@Argument EmployeeInput input)
            throws PositionLevelNotFoundException, DepartmentNotFoundException, EmergencyContactNotFoundException {
        Employee employee = new Employee();
        return setEmployeeInfo(input, employee);
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
    public Employee updateEmployee(@Argument EmployeeInput input)
            throws EmployeeNotFoundException, PositionLevelNotFoundException, DepartmentNotFoundException,
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
            throws PositionLevelNotFoundException, DepartmentNotFoundException, EmergencyContactNotFoundException {
        employee.setFirstName(input.getFirstName());
        employee.setLastName(input.getLastName());
        employee.setEmail(input.getEmail());
        employee.setGender(input.getGender());
        employee.setDateOfBirth(input.getDateOfBirth());
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
                .orElseThrow(() ->
                        new DepartmentNotFoundException("Department not found with id: " + input.getDepartmentId()));
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
