package com.hrms.employeemanagement.specifications;

import com.hrms.employeemanagement.models.EmergencyContact;
import org.springframework.data.jpa.domain.Specification;

public class EmergencyContactSpecifications {
    public static Specification<EmergencyContact> hasId(int id) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("id"), id);
    }
}
