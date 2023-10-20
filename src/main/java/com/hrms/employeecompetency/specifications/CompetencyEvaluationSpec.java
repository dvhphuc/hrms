package com.hrms.employeecompetency.specifications;

import com.hrms.employeecompetency.models.CompetencyEvaluation;
import org.springframework.data.jpa.domain.Specification;

public class CompetencyEvaluationSpec {
    public static Specification<CompetencyEvaluation> getByPositionAndCompCycle(Integer positionId, Integer competencyCycleId) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.and(
                criteriaBuilder.equal(root.get("employee").get("positionLevel").get("position").get("id"), positionId),
                criteriaBuilder.equal(root.get("competencyCycle").get("id"), competencyCycleId)
        );
    }

    public static Specification<CompetencyEvaluation> getByCompCycleAndDepartment(Integer competencyCycleId, Integer departmentId) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.and(
                criteriaBuilder.equal(root.get("competencyCycle").get("id"), competencyCycleId),
                criteriaBuilder.equal(root.get("employee").get("department").get("id"), departmentId)
        );
    }

    public static Specification<CompetencyEvaluation> hasEmployeeId(Integer employeeId) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("employee").get("id"), employeeId);
    }

    public static Specification<CompetencyEvaluation> filterLatestCompetencyCycle(Integer competencyCycleId) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("competencyCycle").get("id"), competencyCycleId);
    }
}
