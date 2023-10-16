package com.hrms.employeecompetency.specifications;

import com.hrms.employeecompetency.models.CompetencyCycle;
import org.springframework.data.jpa.domain.Specification;

public class CompetencyCycleSpecifications {
    public static Specification<CompetencyCycle> hasId(Integer id) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("id"), id);
    }
}
