package com.hrms.employeecompetency.services.impl;

import com.hrms.employeecompetency.dto.EmployeePotentialPerformance;
import com.hrms.employeecompetency.dto.EmployeeRating;
import com.hrms.employeecompetency.models.CompetencyEvaluation;
import com.hrms.employeecompetency.repositories.CompetencyCycleRepository;
import com.hrms.employeecompetency.repositories.CompetencyEvaluationRepository;
import com.hrms.employeecompetency.repositories.EmployeePerformanceRepository;
import com.hrms.employeecompetency.services.CompetencyEvaluationService;
import com.hrms.employeemanagement.models.Employee;
import com.hrms.employeemanagement.repositories.EmployeeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@Service
@Transactional
public class CompetencyEvaluationServiceImpl implements CompetencyEvaluationService {
    @Autowired
    private CompetencyEvaluationRepository competencyEvaluationRepository;

    @Autowired
    private EmployeePerformanceRepository employeePerformanceRepository;

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    private CompetencyCycleRepository competencyCycleRepository;

    @Override
    public List<CompetencyEvaluation> findAll(Specification<CompetencyEvaluation> spec) {
        return competencyEvaluationRepository.findAll(spec);
    }

    @Override
    public List<CompetencyEvaluation> findAll(Specification<CompetencyEvaluation> spec, Sort sort) {
        return competencyEvaluationRepository.findAll(spec, sort);
    }

    @Override
    public Page<CompetencyEvaluation> findAll(Specification<CompetencyEvaluation> spec, Pageable pageable) {
        return competencyEvaluationRepository.findAll(spec, pageable);
    }
    @Override
    @Transactional
    public List<EmployeeRating> findByCompetencyCycleId(int competencyCycleId, int limit) {
        Specification<CompetencyEvaluation> spec = Specification.where(null);
        spec = spec.and((root, query, cb) -> cb.equal(root.get("competencyCycle").get("id"), competencyCycleId));
        List<CompetencyEvaluation> competencyEvaluations = competencyEvaluationRepository.findAll(spec);
        var result = new ArrayList<EmployeeRating>();
        for (Employee e : employeeRepository.findAll()) {
            AtomicInteger rating = new AtomicInteger();
            var employeeScore = competencyEvaluations.stream().filter(ce -> ce.getEmployee().getId() == e.getId()).toList();
            employeeScore.forEach(ce -> rating.updateAndGet(v -> v + ce.getFinalScore().getScore()));
            if (!employeeScore.isEmpty())
                result.add(new EmployeeRating(e, (float) (rating.get()/employeeScore.size())));
            if (result.size() == limit)
                break;
        }
        result.sort((o1, o2) -> o2.getRating().compareTo(o1.getRating()));
        return result;
    }

    @Override
    public List<CompetencyEvaluation> getAllInLatestCompetencyCycle() {
        var latestCompetencyCycle = competencyCycleRepository.findFirstByOrderByStartDateDesc();
        Specification<CompetencyEvaluation> spec = Specification.where(((root, query, builder) ->
                builder.equal(root.get("competencyCycle"), latestCompetencyCycle)));
        return competencyEvaluationRepository.findAll(spec);
    }
    @Override
    public List<EmployeePotentialPerformance> getAllEmployeesPotentialPerformance() {
        return null;
    }

}
