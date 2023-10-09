package com.hrms.employeemanagement.controllers.graphql;

import com.hrms.employeemanagement.models.Department;
import com.hrms.employeemanagement.models.Employee;
import com.hrms.employeemanagement.models.PositionLevel;
import com.hrms.employeemanagement.services.DepartmentService;
import com.hrms.employeemanagement.services.EmployeeService;
import com.hrms.employeemanagement.services.PositionLevelService;
import com.hrms.employeemanagement.specifications.DepartmentSpecifications;
import com.hrms.employeemanagement.specifications.EmployeeSpecifications;
import com.hrms.employeemanagement.specifications.PositionLevelSpecifications;
import jakarta.annotation.Nullable;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.List;

@RestController
public class GraphQLController {
    EmployeeService employeeService;

    PositionLevelService positionLevelService;

    DepartmentService departmentService;

    @Autowired
    public GraphQLController(EmployeeService employeeService, PositionLevelService positionLevelService,
                             DepartmentService departmentService)
    {
        this.employeeService = employeeService;
        this.positionLevelService = positionLevelService;
        this.departmentService = departmentService;
    }

    @Value("${file.upload-dir}")
    private String uploadDir;

    @QueryMapping
    public EmployeeConnection findAllEmployees(@Argument int pageNo, @Argument int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        List<Employee> employeeList = employeeService.findAll(Specification.allOf(), pageable).getContent();
        long totalCount = employeeService.countEmployee();
        long numberOfPages = (long) Math.ceil(((double) totalCount) / pageSize);
        Pagination pagination = new Pagination(pageNo, pageSize, totalCount, numberOfPages);
        return new EmployeeConnection(employeeList, pagination, totalCount);
    }

    @QueryMapping
    public long countEmployees() {
        return employeeService.countEmployee();
    }

    @QueryMapping
    public List<Employee> findEmployeeById(@Argument int id) {
        return employeeService.findAll(EmployeeSpecifications.hasId(id));
    }

    @QueryMapping
    public Iterable<Employee> findNewEmployeeOfMonth() {
        return employeeService.getNewEmployeeOfMonth();
    }

    @QueryMapping
    public EmployeeImageData getEmployeeImage(@Argument int id) throws IOException {
        Employee employee = employeeService.findAll(EmployeeSpecifications.hasId(id)).get(0);
        String imagePath = employee.getProfilePicture();
        Path filePath = Paths.get(uploadDir, imagePath);
        byte[] imageBytes = Files.readAllBytes(filePath);
        String imageBase64 = Base64.getEncoder().encodeToString(imageBytes);
        String imageUrl = uploadDir + "/" + imagePath;
        return new EmployeeImageData(imageUrl, imageBase64);
    }

    @QueryMapping
    public List<Employee> filterEmployees(@Nullable int departmentId,
                                          @Nullable int positionLevelId,
                                          @Nullable boolean isEnabled) {
        return employeeService.findAll(EmployeeSpecifications.hasFilter(departmentId, positionLevelId, isEnabled));
    }

    @MutationMapping
    public Employee createProfile(@Argument String firstName, @Argument String lastName,
                                @Argument String email, @Argument String gender, @Argument String dateOfBirth,
                                @Argument String phoneNumber, @Argument String address, @Argument String dateJoined,
                                @Argument Integer currentContract, @Argument String profileBio,
                                @Argument String facebookLink, @Argument String twitterLink,
                                @Argument String linkedinLink, @Argument String instagramLink,
                                @Argument Integer positionLevelId, @Argument Integer departmentId) {
        Employee employee = new Employee();
        return setEmployeeInfo(firstName, lastName, email, gender, dateOfBirth, phoneNumber, address, dateJoined,
                currentContract, profileBio, facebookLink, twitterLink, linkedinLink, instagramLink, positionLevelId,
                departmentId, employee);
    }

    @MutationMapping
    public boolean inactiveEmployee(@Argument int id) {
        employeeService.findAll(EmployeeSpecifications.hasId(id)).get(0).getUser().setIsEnabled(false);
        return true;
    }

    @MutationMapping
    public Employee updateEmployee(@Argument int id, @Argument String firstName, @Argument String lastName,
                                   @Argument String email, @Argument String gender, @Argument String dateOfBirth,
                                   @Argument String phoneNumber, @Argument String address, @Argument String dateJoined,
                                   @Argument Integer currentContract, @Argument String profileBio,
                                   @Argument String facebookLink, @Argument String twitterLink,
                                   @Argument String linkedinLink, @Argument String instagramLink,
                                   @Argument Integer positionLevelId, @Argument Integer departmentId) {
        Employee employee = employeeService.findAll(EmployeeSpecifications.hasId(id)).get(0);
        return setEmployeeInfo(firstName, lastName, email, gender, dateOfBirth, phoneNumber, address, dateJoined,
                currentContract, profileBio, facebookLink, twitterLink, linkedinLink, instagramLink, positionLevelId,
                departmentId, employee);
    }

    @NotNull
    private Employee setEmployeeInfo(@Argument String firstName, @Argument String lastName, @Argument String email,
                                     @Argument String gender, @Argument String dateOfBirth,
                                     @Argument String phoneNumber, @Argument String address,
                                     @Argument String dateJoined, @Argument Integer currentContract,
                                     @Argument String profileBio, @Argument String facebookLink,
                                     @Argument String twitterLink, @Argument String linkedinLink,
                                     @Argument String instagramLink, @Argument Integer positionLevelId,
                                     @Argument Integer departmentId, Employee employee) {
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
        PositionLevel pl = positionLevelService.findAll(PositionLevelSpecifications.hasId(positionLevelId)).get(0);
        employee.setPositionLevel(pl);
        Department department = departmentService.findAll(DepartmentSpecifications.hasId(departmentId)).get(0);
        employee.setDepartment(department);
        employeeService.saveEmployee(employee);
        return employee;
    }
}
