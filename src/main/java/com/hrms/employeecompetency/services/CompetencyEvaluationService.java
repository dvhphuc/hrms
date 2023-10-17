package com.hrms.employeecompetency.services;

import com.hrms.employeecompetency.dto.EmployeePotentialPerformance;
import com.hrms.employeecompetency.dto.EmployeeRating;
import com.hrms.employeecompetency.models.CompetencyEvaluation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public interface CompetencyEvaluationService {
    List<CompetencyEvaluation> findAll(Specification<CompetencyEvaluation> spec);
    List<CompetencyEvaluation> findAll(Specification<CompetencyEvaluation> spec, Sort sort);
    Page<CompetencyEvaluation> findAll(Specification<CompetencyEvaluation> spec, Pageable pageable);
    List<EmployeeRating> findByCompetencyCycleId(int competencyCycleId, int limit);
    List<EmployeePotentialPerformance> getAllEmployeesPotentialPerformance();

}
