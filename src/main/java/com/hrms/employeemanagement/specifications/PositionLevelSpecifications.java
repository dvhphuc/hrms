package com.hrms.employeemanagement.specifications;

import com.hrms.employeemanagement.models.PositionLevel;
import org.springframework.data.jpa.domain.Specification;

public class PositionLevelSpecifications {
    public static Specification<PositionLevel> hasId(int id) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("id"), id);
    }
}
