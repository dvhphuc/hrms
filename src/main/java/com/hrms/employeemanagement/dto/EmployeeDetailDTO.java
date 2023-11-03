package com.hrms.employeemanagement.dto;

import com.hrms.employeemanagement.models.EmergencyContact;
import com.hrms.employeemanagement.models.Employee;

import java.util.List;

public record EmployeeDetailDTO(Employee employee, List<EmergencyContact> emergencyContacts) {

}
