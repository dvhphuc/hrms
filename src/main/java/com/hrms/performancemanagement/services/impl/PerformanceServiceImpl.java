package com.hrms.performancemanagement.services.impl;

import com.hrms.careerpathmanagement.repositories.PerformanceRangeRepository;
import com.hrms.employeemanagement.repositories.JobLevelRepository;
import com.hrms.employeemanagement.specification.EmployeeSpecification;
import com.hrms.performancemanagement.dto.Dataset;
import com.hrms.performancemanagement.dto.PerformanceByJobLevelChart;
import com.hrms.performancemanagement.model.EmployeePerformance;
import com.hrms.careerpathmanagement.repositories.PerformanceEvaluationRepository;
import com.hrms.performancemanagement.model.PerformanceCycle;
import com.hrms.performancemanagement.repositories.PerformanceCycleRepository;
import com.hrms.performancemanagement.services.PerformanceService;
import com.hrms.performancemanagement.specification.PerformanceSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PerformanceServiceImpl implements PerformanceService {
    @Autowired
    PerformanceEvaluationRepository performanceEvaluationRepository;

    @Autowired
    PerformanceCycleRepository performanceCycleRepository;

    @Autowired
    JobLevelRepository jobLevelRepository;

    @Autowired
    PerformanceRangeRepository performanceRangeRepository;

    @Autowired
    EmployeeSpecification employeeSpecification;

    @Autowired
    PerformanceSpecification performanceSpecification;

    private Integer getLatestCycleId() {
        return performanceCycleRepository.findFirstByOrderByPerformanceCycleIdDesc().getPerformanceCycleId();
    }

    @Override
    public List<PerformanceCycle> getPerformanceCycles() {
        return performanceCycleRepository.findAll();
    }

    @Override
    public Page<EmployeePerformance> getPerformanceEvaluations(Integer empId, Pageable pageable) {
        Specification<EmployeePerformance> spec = employeeSpecification.hasEmployeeId(empId);
        return performanceEvaluationRepository.findAll(spec, pageable);
    }

    public List<EmployeePerformance> getLatestEvaluation(Integer departmentId) {
        Specification<EmployeePerformance> departmentSpec = employeeSpecification.hasDepartmentId(departmentId);
        Specification<EmployeePerformance> cycleSpec = performanceSpecification.hasPerformanceCycleId(getLatestCycleId());

        return performanceEvaluationRepository.findAll(departmentSpec.and(cycleSpec));
    }

    public List<EmployeePerformance> getEvaluations(Integer positionId, Integer performanceCycleId) {
        Specification<EmployeePerformance> positionFilter = employeeSpecification.hasPositionId(positionId);
        Specification<EmployeePerformance> cycleFilter = performanceSpecification.hasPerformanceCycleId(performanceCycleId);

        return performanceEvaluationRepository.findAll(positionFilter.and(cycleFilter));
    }

    public PerformanceByJobLevelChart getPerformanceByLevelChart(Integer positionId, Integer cycleId) {
        Specification<EmployeePerformance> cycleSpec = performanceSpecification.hasPerformanceCycleId(cycleId);
        Specification<EmployeePerformance> posSpec = employeeSpecification.hasPositionId(positionId);
        var evaluations = performanceEvaluationRepository.findAll(cycleSpec.and(posSpec));


        //"Unsatisfactory", "Partially meet expectation", "Meet expectation", "Exceed expectation", "Outstanding"
        var labels = jobLevelRepository.findAll();

        var datasets = performanceRangeRepository.findAll().stream()
                .map(range -> {
                    var dataset = new Dataset(range.getText(), new ArrayList());
                    labels.forEach(jobLevel -> {
                        var count = (float) evaluations.stream()
                                .filter(evaluation -> evaluation.getEmployee().getJobLevel().getId() == jobLevel.getId())
                                .filter(evaluation -> evaluation.getFinalAssessment() >= range.getMinValue())
                                .filter(evaluation -> evaluation.getFinalAssessment() <= range.getMaxValue())
                                .count();
                        dataset.getData().add(count);
                    });
                    return dataset;
                })
                .toList();

        return new PerformanceByJobLevelChart(labels, datasets);
    }
}
