package com.hrms.performancemanagement.services;

import com.hrms.performancemanagement.dto.PerformanceByJobLevelChart;
import com.hrms.performancemanagement.model.PerformanceEvaluation;
import com.hrms.performancemanagement.model.PerformanceCycle;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PerformanceService {
    List<PerformanceCycle> getPerformanceCycles();
    Page<PerformanceEvaluation> getPerformanceEvaluations(Integer empId, Pageable pageable);
    PerformanceByJobLevelChart getPerformanceStatisticByJobLevel(Integer positionId, Integer cycleId);
}
