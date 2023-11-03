package com.hrms.performancemanagement.controllers;

import com.hrms.global.paging.Pagination;
import com.hrms.performancemanagement.dto.EmployeePerformanceRatingScore;
import com.hrms.performancemanagement.dto.PerformanceByJobLevalChartDTO;
import com.hrms.performancemanagement.dto.PerformanceRatingScorePaging;
import com.hrms.performancemanagement.model.PerformanceEvaluation;
import com.hrms.performancemanagement.services.PerformanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.hrms.global.paging.PaginationSetup.setupPaging;


@RestController
@CrossOrigin(origins = "*")
public class PerformanceController {
    @Autowired
    PerformanceService performanceService;
    @QueryMapping(name = "employeePerformanceRatingScore")
    public PerformanceRatingScorePaging getEmployeePerformanceRatingScore(@Argument(name = "employeeId") Integer empId,
                                                                          @Argument int pageNo,
                                                                          @Argument int pageSize)
    {
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize);
        performanceService.getAllPerformanceCycles();
        Page<PerformanceEvaluation> empPerformances = performanceService.getPerformanceEvaluations(empId, pageable);
        List<EmployeePerformanceRatingScore> data = empPerformances.stream()
                .map(empPerformance ->
                        new EmployeePerformanceRatingScore(empPerformance.getPerformanceCycle().getPerformanceCycleName(),
                                empPerformance.getFinalAssessment()))
                .toList();
        Pagination pagination = setupPaging(empPerformances.getTotalElements(), pageNo, pageSize);
        return new PerformanceRatingScorePaging(data, pagination);
    }

    @QueryMapping
    public PerformanceByJobLevalChartDTO performanceByJobLevel(@Argument Integer positionId,
                                                               @Argument Integer cycleId)
    {
        return performanceService.getPerformanceStatistic(positionId, cycleId);
    }
}
