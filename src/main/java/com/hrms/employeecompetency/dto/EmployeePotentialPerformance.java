package com.hrms.employeecompetency.dto;

import com.hrms.employeemanagement.models.Employee;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmployeePotentialPerformance {
    Employee employee;
    String profileImgUri;
    int potential;
    int performance;
}
