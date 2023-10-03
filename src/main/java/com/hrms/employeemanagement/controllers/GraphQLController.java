package com.hrms.employeemanagement.controllers;

import com.hrms.employeemanagement.models.Employee;
import com.hrms.employeemanagement.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class GraphQLController {


    @Autowired
    private EmployeeService employeeService;

    @QueryMapping
    public Iterable<Employee> findAllEmployees() {
        return employeeService.getAllEmployees();
    }

    @QueryMapping
    public long countEmployees() {
        return employeeService.countEmployee();
    }

    @QueryMapping
    public Optional<Employee> findEmployeeById(@Argument int id) {
        return employeeService.getEmployeeById(id);
    }

    @QueryMapping
    public Iterable<Employee> findNewEmployeeOfMonth() {
        return employeeService.getNewEmployeeOfMonth();
    }

    @MutationMapping
    public Employee newEmployee(@Argument String firstName, @Argument String lastName,
                                @Argument String email, @Argument String gender, @Argument String dateOfBirth,
                                @Argument String phoneNumber, @Argument String address, @Argument String positionLevel) {
        Employee employee = new Employee();
        employee.setFirstName(firstName);
        employee.setLastName(lastName);
        employee.setEmail(email);
        employee.setGender(gender);
        employee.setDateOfBirth(dateOfBirth);
        employee.setPhoneNumber(phoneNumber);
        employee.setAddress(address);
        employee.setPositionLevel(positionLevel);
        employeeService.saveEmployee(employee);
        return employee;
    }

    @MutationMapping
    public boolean deleteEmployee(@Argument int id) {
        employeeService.deleteEmployeeById(id);
        return true;
    }

    @MutationMapping
    public Employee updateEmployee(@Argument int id, @Argument String firstName, @Argument String lastName,
                                   @Argument String email, @Argument String gender, @Argument String dateOfBirth,
                                   @Argument String phoneNumber, @Argument String address, @Argument String positionLevel) {
        Employee employee = new Employee();
        employee.setId(id);
        employee.setFirstName(firstName);
        employee.setLastName(lastName);
        employee.setEmail(email);
        employee.setGender(gender);
        employee.setDateOfBirth(dateOfBirth);
        employee.setPhoneNumber(phoneNumber);
        employee.setAddress(address);
        employee.setPositionLevel(positionLevel);
        employeeService.saveEmployee(employee);
        return employee;
    }
}
