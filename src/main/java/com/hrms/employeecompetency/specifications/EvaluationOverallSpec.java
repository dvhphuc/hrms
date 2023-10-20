package com.hrms.employeecompetency.specifications;

import com.hrms.employeecompetency.models.EvaluationOverall;
import org.springframework.data.jpa.domain.Specification;

public class EvaluationOverallSpec {
    public static Specification<EvaluationOverall> getByEmployeeAndEmpStatusComplete(Integer employeeId, Integer cycleId) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.and(
                criteriaBuilder.equal(root.get("employee").get("id"), employeeId),
                criteriaBuilder.equal(root.get("employeeStatus"), "Completed"),
                criteriaBuilder.equal(root.get("competencyCycle").get("id"), cycleId)
        );
    }

    public static Specification<EvaluationOverall> getByEmployeeAndEvaStatusComplete(Integer employeeId, Integer cycleId) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.and(
                criteriaBuilder.equal(root.get("employee").get("id"), employeeId),
                criteriaBuilder.equal(root.get("evaluatorStatus"), "Completed"),
                criteriaBuilder.equal(root.get("competencyCycle").get("id"), cycleId)
        );
    }
}
