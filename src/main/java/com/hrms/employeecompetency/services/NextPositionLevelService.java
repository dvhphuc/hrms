package com.hrms.employeecompetency.services;

import com.hrms.employeemanagement.models.PositionLevel;

public interface NextPositionLevelService {
    PositionLevel getNextPositionLevelByCurrentPositionLevelId(int currentPositionLevelId);
}
