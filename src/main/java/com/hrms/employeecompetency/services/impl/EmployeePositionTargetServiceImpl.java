package com.hrms.employeecompetency.services.impl;

import com.hrms.employeecompetency.models.EmployeePositionTarget;
import com.hrms.employeecompetency.models.PositionLevel;
import com.hrms.employeecompetency.repositories.EmployeePositionTargetRepository;
import com.hrms.employeecompetency.services.EmployeePositionTargetService;
import com.hrms.employeemanagement.repositories.EmployeeRepository;
import com.hrms.employeemanagement.repositories.PositionLevelRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class EmployeePositionTargetServiceImpl implements EmployeePositionTargetService {
    @Autowired
    EmployeePositionTargetRepository employeePositionTargetRepository;

    @Autowired
    PositionLevelRepository positionLevelRepository;
    @Autowired
    EmployeeRepository employeeRepository;

    @Override
    public void setCareerPath(Integer employeeId, Integer positionLevelId) {
        var employeePositionTarget = new EmployeePositionTarget();
        employeePositionTarget.setEmployee(employeeRepository.getById(employeeId));
        employeePositionTarget.setPositionLevel(positionLevelRepository.findById(positionLevelId).get());
        employeePositionTargetRepository.save(employeePositionTarget);
    }

    @Override
    public List<PositionLevel> getCareerPath(Integer employeeId) {
        return employeePositionTargetRepository.findAllByEmployeeId(employeeId);
    }
}
