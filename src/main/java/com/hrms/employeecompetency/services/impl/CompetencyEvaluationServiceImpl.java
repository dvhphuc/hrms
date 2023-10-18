package com.hrms.employeecompetency.services.impl;

import com.hrms.employeecompetency.dto.EmployeeRating;
import com.hrms.employeecompetency.models.CompetencyEvaluation;
import com.hrms.employeecompetency.repositories.CompetencyEvaluationRepository;
import com.hrms.employeecompetency.services.CompetencyEvaluationService;
import com.hrms.employeemanagement.models.Employee;
import com.hrms.employeemanagement.repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Transactional
public class CompetencyEvaluationServiceImpl implements CompetencyEvaluationService {
    @Autowired
    private CompetencyEvaluationRepository competencyEvaluationRepository;

    @Autowired
    EmployeeRepository employeeRepository;
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
    public List<EmployeeRating> findAllByCompetencyCycleId(Integer competencyCycleId) {
        Specification<CompetencyEvaluation> spec = Specification.where(null);
        spec = spec.and((root, query, cb) -> cb.equal(root.get("competencyCycle").get("id"), competencyCycleId));
        List<CompetencyEvaluation> competencyEvaluations = competencyEvaluationRepository.findAll(spec);
        return  competencyEvaluations.parallelStream()
                .map(competencyEvaluation -> {
                    Employee employee = employeeRepository.findById(competencyEvaluation.getEmployee().getId()).get();
                    return new EmployeeRating(employee, (float) (competencyEvaluation.getProficiencyLevel().getWeight()));
                })
                .collect(Collectors.toMap(
                        e -> e.getEmployee().getId(),
                        Function.identity(),
                        (e1, e2) -> {
                            e1.setRating((e1.getRating() + e2.getRating())/2);
                            return e1;
                        }
                ))
                .values()
                .stream().sorted(Comparator.comparing(EmployeeRating::getRating).reversed()).collect(Collectors.toList());
    }
}
