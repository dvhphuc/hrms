package com.hrms.employeecompetency.specifications;

import com.hrms.employeecompetency.models.SkillSetEvaluation;
import org.springframework.data.jpa.domain.Specification;


public class SkillSetEvaluationSpecifications {
    public static Specification<SkillSetEvaluation> hasTop10ProficiencyLevelInCycle(Integer cycleId) {
        return (root, query, builder) -> {
            query.orderBy(builder.desc(root.get("finalProficiencyLevel")));
            query.where(builder.equal(root.get("competencyCycle").get("id"), cycleId));
            return query.getRestriction();
        };
    }
}
