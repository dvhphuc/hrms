package com.hrms.employeecompetency.specifications;

import com.hrms.employeecompetency.models.EvaluationOverall;
import org.springframework.data.jpa.domain.Specification;

public class EvaluationOverallSpecifications {
    public static Specification<EvaluationOverall> hasEmployeeIdAndStatusCompleted(int id) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.and(
                criteriaBuilder.equal(root.get("employee").get("id"), id),
                criteriaBuilder.equal(root.get("status"), "Completed")
        );
    }

}
