package com.hrms.careerpathmanagement.specification;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CompetencySpecification {
    public <T> Specification<T> hasCycleId(Integer cycleId) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("competencyCycle").get("id"), cycleId);
    }

    public <T> Specification<T> hasCycleIds(List<Integer> cycleIds) {
        return (root, query, cb) -> root.get("competencyCycle").get("id").in(cycleIds);
    }
}