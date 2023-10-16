package com.hrms.employeecompetency.specifications;

import com.hrms.employeecompetency.models.CompetencyEvaluation;
import org.springframework.data.jpa.domain.Specification;

public class CompetencyEvaluationSpecifications {
    public static Specification<CompetencyEvaluation> hasPositionAndCompetencyCycle(Integer positionId, Integer competencyCycleId) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.and(
                criteriaBuilder.equal(root.get("employee").get("positionLevel").get("position").get("id"), positionId),
                criteriaBuilder.equal(root.get("competencyCycle").get("id"), competencyCycleId)
        );
    }
}
