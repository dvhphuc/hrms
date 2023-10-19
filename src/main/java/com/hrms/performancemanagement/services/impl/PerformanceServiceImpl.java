package com.hrms.performancemanagement.services.impl;

import com.hrms.performancemanagement.model.EmployeePerformance;
import com.hrms.employeecompetency.repositories.EmployeePerformanceRepository;
import com.hrms.performancemanagement.model.PerformanceCycle;
import com.hrms.performancemanagement.repositories.PerformanceCycleRepository;
import com.hrms.performancemanagement.services.PerformanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PerformanceServiceImpl implements PerformanceService {
    @Autowired
    EmployeePerformanceRepository employeePerformanceRepository;

    @Autowired
    PerformanceCycleRepository performanceCycleRepository;

    @Override
    public Integer countByRatingRangeAndPerformanceCycleId(Integer min, Integer max, Integer performanceCycleId) {
        Specification<EmployeePerformance> spec = Specification.where(((root, query, builder) ->
                builder.between(root.get("finalAssessment"), min, max)));
        Specification<EmployeePerformance> filterByPerformanceCycle = Specification.where(((root, query, builder) ->
                builder.equal(root.get("performanceCycle").get("performanceCycleId"), performanceCycleId)));
        return employeePerformanceRepository.findAll(Specification.where(spec).and(filterByPerformanceCycle)).size();
    }

    @Override
    public List<EmployeePerformance> findByPerformanceCycleId(Integer performanceCycleId, Integer limit) { //Integer -> int
        // Latest performance cycle
        // Paging
        Specification<EmployeePerformance> spec = Specification.where(((root, query, builder) ->
                builder.equal(root.get("performanceCycle").get("performanceCycleId"), performanceCycleId)));
        Sort sort = Sort.by(Sort.Direction.DESC, "finalAssessment");
        if (limit == null) {
            return employeePerformanceRepository.findAll(spec, sort);
        }
        return employeePerformanceRepository.findAll(spec, sort).subList(0, limit);
    }

    @Override
    public Page<EmployeePerformance> findAll(Pageable pageable) {
        var latestPerformanceCycle = performanceCycleRepository.findAll(Sort.by(Sort.Direction.DESC, "performanceCycleId")).get(0);
        Specification<EmployeePerformance> spec = Specification.where(((root, query, builder) ->
                builder.equal(root.get("performanceCycle"), latestPerformanceCycle)));
        return employeePerformanceRepository.findAll(spec, pageable);
    }

    @Override
    public EmployeePerformance findByEmployeeIdAndPerformanceCyclePerformanceCycleId(int employeeId, int performanceCycleId) {
        Specification<EmployeePerformance> filterByEmployee = Specification.where(((root, query, builder) ->
                builder.equal(root.get("employee").get("id"), employeeId)));
        Specification<EmployeePerformance> filterByPerformanceCycle = Specification.where(((root, query, builder) ->
                builder.equal(root.get("performanceCycle").get("performanceCycleId"), performanceCycleId)));
        return employeePerformanceRepository.findAll(Specification.where(filterByEmployee).and(filterByPerformanceCycle))
                .get(0);
    }

    @Override
    public PerformanceCycle findLatestPerformanceCycleOfEmployee(int employeeId) {
        Specification<EmployeePerformance> filterByEmployee = Specification.where(((root, query, builder) ->
                builder.equal(root.get("employee").get("id"), employeeId)));
        if (employeePerformanceRepository.findAll(filterByEmployee).size() == 0) {
            return null;
        }
        return employeePerformanceRepository.findAll(filterByEmployee).get(0).getPerformanceCycle();
    }

    @Override
    public List<EmployeePerformance> findAll(Specification<EmployeePerformance> spec) {
        return employeePerformanceRepository.findAll(spec);
    }

    @Override
    public List<EmployeePerformance> findAll(Specification<EmployeePerformance> spec, Sort sort) {
        return employeePerformanceRepository.findAll(spec, sort);
    }

    @Override
    public Page<EmployeePerformance> findAll(Specification<EmployeePerformance> spec, Pageable pageable) {
        return employeePerformanceRepository.findAll(spec, pageable);
    }

    @Override
    public List<EmployeePerformance> findAllByPositionIdAndPerformanceCycleId(Integer positionId, Integer performanceCycleId) {
        Specification<EmployeePerformance> filterByPosition = Specification.where(((root, query, builder) ->
                builder.equal(root.get("employee").get("positionLevel").get("position").get("id"), positionId)));
        Specification<EmployeePerformance> filterByPerformanceCycle = Specification.where(((root, query, builder) ->
                builder.equal(root.get("performanceCycle").get("performanceCycleId"), performanceCycleId)));
        return employeePerformanceRepository.findAll(Specification.where(filterByPosition).and(filterByPerformanceCycle));
    }

    @Override
    public List<EmployeePerformance> filterCategory(String category, List<EmployeePerformance> employeePerformances) {
        return employeePerformances.stream().filter(
                employeePerformance -> employeePerformance.getFinalAssessment() >= 1 && employeePerformance.getFinalAssessment() <= 5
        ).toList();
    }
}
