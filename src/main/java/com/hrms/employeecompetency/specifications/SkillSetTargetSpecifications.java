package com.hrms.employeecompetency.specifications;

import com.hrms.employeecompetency.models.SkillSetTarget;
import org.springframework.data.jpa.domain.Specification;

public class SkillSetTargetSpecifications {
    public static Specification<SkillSetTarget> getDescByCycleAndEmployee(Integer competencyCycleId, Integer employeeId) {
        return (root, query, builder) -> {
            query.orderBy(builder.desc(root.get("targetProficiencyLevel")));
            query.where(builder.equal(root.get("competencyCycle").get("id"), competencyCycleId),
                    builder.equal(root.get("employee").get("id"), employeeId));
            return query.getRestriction();
        };
    }
}
