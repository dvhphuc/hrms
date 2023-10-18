package com.hrms.employeecompetency.specifications;

import com.hrms.employeecompetency.models.CompetencyTimeLine;
import org.springframework.data.jpa.domain.Specification;

public class CompetencyTimeLineSpecifications {
    public static Specification<CompetencyTimeLine> hasCompetencyCycle(Integer competencyCycleId) {
        return (root, query, cb) -> cb.equal(root.get("competencyCycle").get("id"), competencyCycleId);
    }
}
