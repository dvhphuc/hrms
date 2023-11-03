package com.hrms.performancemanagement.services.impl;

import com.hrms.careerpathmanagement.models.PerformanceRange;
import com.hrms.careerpathmanagement.repositories.PerformanceRangeRepository;
import com.hrms.employeemanagement.models.JobLevel;
import com.hrms.employeemanagement.repositories.EmployeeRepository;
import com.hrms.employeemanagement.repositories.JobLevelRepository;
import com.hrms.employeemanagement.specification.EmployeeSpecification;
import com.hrms.performancemanagement.dto.Dataset;
import com.hrms.performancemanagement.dto.PerformanceByJobLevalChartDTO;
import com.hrms.performancemanagement.model.PerformanceEvaluation;
import com.hrms.careerpathmanagement.repositories.PerformanceEvaluationRepository;
import com.hrms.performancemanagement.model.PerformanceCycle;
import com.hrms.performancemanagement.repositories.PerformanceCycleRepository;
import com.hrms.performancemanagement.services.PerformanceService;
import com.hrms.performancemanagement.specification.PerformanceSpecification;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class PerformanceServiceImpl implements PerformanceService {
    @PersistenceContext
    EntityManager em;

    @Autowired
    PerformanceEvaluationRepository performanceEvaluationRepository;

    @Autowired
    PerformanceCycleRepository performanceCycleRepository;

    @Autowired
    JobLevelRepository jobLevelRepository;

    @Autowired
    PerformanceRangeRepository performanceRangeRepository;

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    EmployeeSpecification employeeSpecification;

    @Autowired
    PerformanceSpecification performanceSpecification;

    private Integer getLatestCycleId() {
        return performanceCycleRepository.findFirstByOrderByPerformanceCycleIdDesc().getPerformanceCycleId();
    }

    @Override
    public List<PerformanceCycle> getAllPerformanceCycles() {
        return performanceCycleRepository.findAll();
    }

    @Override
    public Page<PerformanceEvaluation> getPerformanceEvaluations(Integer empId, Pageable pageable) {
        Specification<PerformanceEvaluation> spec = employeeSpecification.hasEmployeeId(empId);
        return performanceEvaluationRepository.findAll(spec, pageable);
    }

    public List<PerformanceEvaluation> getLatestEvaluations(Integer departmentId) {
        Specification<PerformanceEvaluation> departmentSpec = employeeSpecification.hasDepartmentId(departmentId);
        Specification<PerformanceEvaluation> cycleSpec = performanceSpecification.hasPerformanceCycleId(getLatestCycleId());

        return performanceEvaluationRepository.findAll(departmentSpec.and(cycleSpec));
    }

    public List<PerformanceEvaluation> getEvaluations(Integer positionId, Integer performanceCycleId) {
        Specification<PerformanceEvaluation> positionFilter = employeeSpecification.hasPositionId(positionId);
        Specification<PerformanceEvaluation> cycleFilter = performanceSpecification.hasPerformanceCycleId(performanceCycleId);

        return performanceEvaluationRepository.findAll(positionFilter.and(cycleFilter));
    }

    @Override
    public PerformanceByJobLevalChartDTO getPerformanceStatistic(Integer positionId, Integer cycleId) {
        var evaluations = performanceEvaluationRepository.findByCycleIdAndPositionId(positionId, cycleId);
        var performanceRanges = performanceRangeRepository.findAll();
        var labels = jobLevelRepository.findAll();
        var datasets = createDatasets(evaluations, performanceRanges, labels);

        return new PerformanceByJobLevalChartDTO(labels, datasets);
   }

    private List<Dataset> createDatasets(List<PerformanceEvaluation> evaluations,
                                         List<PerformanceRange> performanceRanges,
                                         List<JobLevel> labels)
    {
        List<Dataset> datasets = new ArrayList<>();

        performanceRanges.forEach(range -> {
            var dataset = createDatasetForRange(evaluations, range, labels);
            datasets.add(dataset);
        });

        return datasets;
    }

    private Dataset createDatasetForRange(List<PerformanceEvaluation> evaluations,
                                          PerformanceRange range,
                                          List<JobLevel> labels)
    {
        Dataset dataset = new Dataset(range.getText(), new ArrayList<>());
        labels.forEach( label -> {
            long countEvalsInPositionLevel = countEvals(label, evaluations);
            long count = countPerformancesInRange(label, range, evaluations);
            float percentage = calculatePercentage(count, countEvalsInPositionLevel);
            dataset.getData().add(percentage);
        });
        return dataset;
    }

    private long countEvals(JobLevel jobLevel, List<PerformanceEvaluation> evaluations) {
        return evaluations.stream()
                .filter(e -> e.getEmployee().getJobLevel().getId() == jobLevel.getId())
                .count();
    }

    private long countPerformancesInRange(JobLevel jobLevel, PerformanceRange range, List<PerformanceEvaluation> evaluations) {
        return evaluations.stream()
                .filter(eval -> eval.getEmployee().getJobLevel().getId() == jobLevel.getId())
                .filter(eval -> eval.getFinalAssessment() >= range.getMinValue())
                .filter(eval -> eval.getFinalAssessment() <= range.getMaxValue())
                .count();
    }

    private float calculatePercentage(long amount, long total) {
        return total == 0 ? 0 : (float) (amount * 100) / total;
    }

}


//"Unsatisfactory", "Partially meet expectation", "Meet expectation", "Exceed expectation", "Outstanding"
//        var labels = jobLevelRepository.findAll();
//
//        Map<JobLevel, Integer> jobLevelCount = countEmpInJoblevel();
//
//        var datasets = performanceRangeRepository.findAll().stream()
//                .map(range -> {
//                    var dataset = new Dataset(range.getText(), new ArrayList());
//                    labels.forEach(level -> {
//                        Specification<PerformanceEvaluation> posSpec = employeeSpecification.hasPositionId(positionId);
//                        Specification<PerformanceEvaluation> levelSpec = employeeSpecification.hasJobLevelId(level.getId());
//                        var countEmpIsLevelPosition = performanceEvaluationRepository.count(posSpec.and(levelSpec));
//                        var count = (float) evaluations.stream()
//                                .filter(eval -> eval.getEmployee().getJobLevel().getId() == level.getId())
//                                .filter(eval -> eval.getFinalAssessment() >= range.getMinValue())
//                                .filter(eval -> eval.getFinalAssessment() <= range.getMaxValue())
//                                .count();
//                        dataset.getData().add( (float)  (count / countEmpIsLevelPosition) * 100);
//                    });
//                    return dataset;
//                })
//                .toList();
//
//
//        return new PerformanceByJobLevelChart(labels.stream().map(l -> l.getJobLevelName()).toList(),
//                datasets);
//    }
//
//    private HashMap<JobLevel, Integer> countEmpInJoblevel() {
//        HashMap<Job, Integer> countMap = new HashMap<>();
//


//THIS SOLUTION IS NOT WORKING FOR N+1 PROBLEM
//Specification<PerformanceEvaluation> spec = (root, query, cb) -> {
//    Join<PerformanceEvaluation, Employee> employeeJoin = root.join("employee");
//    Join<Employee, Position> positionJoin = employeeJoin.join("position");
//    Join<PerformanceEvaluation, PerformanceCycle> cycleJoin = root.join("performanceCycle");
//    return cb.and(
//            cb.equal(positionJoin.get("id"), positionId),
//            cb.equal(cycleJoin.get("performanceCycleId"), cycleId)
//    );
//};
//
//    CriteriaQuery<PerformanceEvaluation> criteriaQuery = em.getCriteriaBuilder().createQuery(PerformanceEvaluation.class);
//    Root<PerformanceEvaluation> root = criteriaQuery.from(PerformanceEvaluation.class);
//        criteriaQuery.where(spec.toPredicate(root, criteriaQuery, em.getCriteriaBuilder()));
//                root.join("employee");
//                root.join("performanceCycle");
//                root.join("employee").join("position");
//                root.join("employee").join("jobLevel");
//                TypedQuery<PerformanceEvaluation> query = em.createQuery(criteriaQuery);
//        List<PerformanceEvaluation> evaluations = query.getResultList();