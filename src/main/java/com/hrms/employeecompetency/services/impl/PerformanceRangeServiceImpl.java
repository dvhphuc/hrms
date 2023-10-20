package com.hrms.employeecompetency.services.impl;

import com.hrms.employeecompetency.models.PerformanceRange;
import com.hrms.employeecompetency.repositories.PerformanceRangeRepository;
import com.hrms.employeecompetency.services.PerformanceRangeService;
import kotlin.jvm.internal.SerializedIr;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PerformanceRangeServiceImpl implements PerformanceRangeService {
    @Autowired
    private PerformanceRangeRepository performanceRangeRepository;
    @Override
    public List<PerformanceRange> getAllPerformanceRange() {
        return performanceRangeRepository.findAll();
    }
}
