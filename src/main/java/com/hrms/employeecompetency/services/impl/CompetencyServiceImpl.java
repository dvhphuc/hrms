package com.hrms.employeecompetency.services.impl;

import com.hrms.employeecompetency.dto.EmployeeRating;
import com.hrms.employeecompetency.models.CompetencyEvaluation;
import com.hrms.employeecompetency.repositories.CompetencyEvaluationRepository;
import com.hrms.employeecompetency.services.CompetencyService;
import com.hrms.employeemanagement.models.Employee;
import com.hrms.employeemanagement.repositories.EmployeeRepository;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class CompetencyServiceImpl implements CompetencyService {
    @Autowired
    CompetencyEvaluationRepository competencyEvaluationRepository;

    @Autowired
    EmployeeRepository employeeRepository;
    @Override
    @Transactional
    public List<EmployeeRating> findAllByCompetencyCycleId(Integer competencyCycleId) {
        Specification<CompetencyEvaluation> spec = Specification.where(null);
        spec = spec.and((root, query, cb) -> cb.equal(root.get("competencyCycle").get("id"), competencyCycleId));
        List<CompetencyEvaluation> competencyEvaluations = competencyEvaluationRepository.findAll(spec);
        return  competencyEvaluations.parallelStream()
                .map(competencyEvaluation -> {
                    Employee employee = employeeRepository.findById(competencyEvaluation.getEmployee().getId()).get();
                    return new EmployeeRating(employee, (float) (competencyEvaluation.getFinalScore()*1.0));
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
