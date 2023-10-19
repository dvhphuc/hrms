package com.hrms.employeecompetency.services.impl;

import com.hrms.employeecompetency.models.EmployeeCareerPath;
import com.hrms.employeecompetency.repositories.EmployeeCareerPathRepository;
import com.hrms.employeecompetency.services.EmployeeCareerPathService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeCareerPathServiceImpl implements EmployeeCareerPathService {
    @Autowired
    EmployeeCareerPathRepository employeeCareerPathRepository;

    @Override
    public EmployeeCareerPath save(EmployeeCareerPath employeeCareerPath) {
        return employeeCareerPathRepository.save(employeeCareerPath);
    }

    @Override
    public List<EmployeeCareerPath> findAllByEmployeeId(int employeeId) {
        return employeeCareerPathRepository.findAllByEmployeeId(employeeId);
    }
}
