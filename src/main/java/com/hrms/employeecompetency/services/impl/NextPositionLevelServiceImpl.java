package com.hrms.employeecompetency.services.impl;

import com.hrms.employeecompetency.repositories.NextPositionLevelRepository;
import com.hrms.employeecompetency.services.NextPositionLevelService;
import com.hrms.employeemanagement.models.PositionLevel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NextPositionLevelServiceImpl implements NextPositionLevelService {
    @Autowired
    NextPositionLevelRepository nextPositionLevelRepository;
    @Override
    public PositionLevel getNextPositionLevelByCurrentPositionLevelId(int currentPositionLevelId) {
        return nextPositionLevelRepository.findFirstByCurrentId(currentPositionLevelId);
    }
}
