package com.hrms.employeecompetency.services;

import com.hrms.employeecompetency.models.EmployeeCareerPath;

import java.util.List;

public interface EmployeeCareerPathService {
    EmployeeCareerPath save(EmployeeCareerPath employeeCareerPath);
    List<EmployeeCareerPath> findAllByEmployeeId(int employeeId);
}
