package com.hrms.employeemanagement.specifications;

import com.hrms.employeecompetency.models.Position;
import org.springframework.data.jpa.domain.Specification;

public class PositionSpecifications {
    public static Specification<Position> hasId(int id) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("id"), id);
    }
}
