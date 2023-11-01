package com.hrms.performancemanagement.specification;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class PerformanceSpecification {
    public <T> Specification<T> hasPerformanceCycleId(Integer performanceCycleId) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("performanceCycle").get("performanceCycleId"), performanceCycleId);
    }
}
