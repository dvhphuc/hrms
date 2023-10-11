package com.hrms.employeemanagement.specifications;

import com.hrms.employeecompetency.models.PositionLevel;
import org.springframework.data.jpa.domain.Specification;

public class PositionLevelSpecifications {
    public static Specification<PositionLevel> hasId(int id) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("id"), id);
    }
    public static Specification<PositionLevel> hasPositionIdAndJobLevelID(int positionId, int jobLevelId) {
        //if jobLevelId == null, then return only positionId
        if (jobLevelId == 0) {
            return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("position").get("id"), positionId);
        }
        return (root, query, criteriaBuilder) -> criteriaBuilder.and(
                criteriaBuilder.equal(root.get("position").get("id"), positionId),
                criteriaBuilder.equal(root.get("jobLevel").get("id"), jobLevelId)
        );
    }
}
