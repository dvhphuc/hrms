package com.hrms.performancemanagement.services;

import com.hrms.performancemanagement.dto.PerformanceByJobLevalChartDTO;
import com.hrms.performancemanagement.model.PerformanceEvaluation;
import com.hrms.performancemanagement.model.PerformanceCycle;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PerformanceService {
    List<PerformanceCycle> getAllPerformanceCycles();
    Page<PerformanceEvaluation> getPerformanceEvaluations(Integer empId, Pageable pageable);
    PerformanceByJobLevalChartDTO getPerformanceStatistic(Integer positionId, Integer cycleId);
}
