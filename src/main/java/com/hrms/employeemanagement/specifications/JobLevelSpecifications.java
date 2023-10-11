package com.hrms.employeemanagement.specifications;

import com.hrms.employeecompetency.models.JobLevel;
import org.springframework.data.jpa.domain.Specification;

public class JobLevelSpecifications {
    public static Specification<JobLevel> hasId(int id) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("id"), id);
    }
}
