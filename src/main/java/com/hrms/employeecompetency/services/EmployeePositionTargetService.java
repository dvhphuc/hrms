package com.hrms.employeecompetency.services;

import com.hrms.employeemanagement.models.PositionLevel;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface EmployeePositionTargetService {
    void setCareerPath(Integer employeeId, Integer positionId);
    List<PositionLevel> getCareerPath(Integer employeeId);
}
