package com.hrms.performancemanagement.services.impl;

import com.hrms.careerpathmanagement.repositories.PerformanceRangeRepository;
import com.hrms.employeemanagement.repositories.JobLevelRepository;
import com.hrms.performancemanagement.dto.Dataset;
import com.hrms.performancemanagement.dto.PerformanceByJobLevelChart;
import com.hrms.performancemanagement.model.EmployeePerformance;
import com.hrms.careerpathmanagement.repositories.PerformanceEvaluationRepository;
import com.hrms.performancemanagement.model.PerformanceCycle;
import com.hrms.performancemanagement.repositories.PerformanceCycleRepository;
import com.hrms.performancemanagement.services.PerformanceService;
import jakarta.persistence.criteria.Predicate;
import lombok.EqualsAndHashCode;
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

    private Integer getLatestCycleId() {
        Pageable pageable = PageRequest.of(0, 1, Sort.by("performanceCycleId").descending());
        return performanceCycleRepository.findAll(pageable).getContent().get(0).getPerformanceCycleId();
    }

    @Override
    public List<PerformanceCycle> getPerformanceCycles() {
        return performanceCycleRepository.findAll();
    }

    @Override
    public Page<EmployeePerformance> getPerformances(Integer empId, Pageable pageable) {
        Specification<EmployeePerformance> spec = ((root, query, criteriaBuilder)
                -> criteriaBuilder.equal(root.get("employee").get("id"), empId));
        return performanceEvaluationRepository.findAll(spec, pageable);
    }

    public List<EmployeePerformance> getEvaluations(Integer departmentId) {
        Specification<EmployeePerformance> departmentSpec = Specification.where(((root, query, builder) ->
                builder.equal(root.get("employee").get("department").get("departmentId"), departmentId)));
        Specification<EmployeePerformance> cycleSpec = Specification.where(((root, query, builder) ->
                builder.equal(root.get("performanceCycle").get("performanceCycleId"), getLatestCycleId())));

        return performanceEvaluationRepository.findAll(departmentSpec.and(cycleSpec));
    }

    public List<EmployeePerformance> getEvaluations(Integer positionId, Integer performanceCycleId) {
        Specification<EmployeePerformance> positionFilter = Specification.where(((root, query, builder) ->
                builder.equal(root.get("employee").get("position").get("positionId"), positionId)));

        Specification<EmployeePerformance> cycleFilter = Specification.where(((root, query, builder) ->
                builder.equal(root.get("performanceCycle").get("performanceCycleId"), performanceCycleId)));

        return performanceEvaluationRepository.findAll(positionFilter.and(cycleFilter));
    }

    public PerformanceByJobLevelChart getPerformanceByLevelChart(Integer positionId, Integer cycleId) {
        Specification<EmployeePerformance> cycleSpec = Specification.where(((root, query, builder) ->
                builder.equal(root.get("performanceCycle").get("performanceCycleId"), cycleId)));
        Specification<EmployeePerformance> posSpec = Specification.where(((root, query, builder) ->
                builder.equal(root.get("employee").get("position").get("positionId"), positionId)));
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
