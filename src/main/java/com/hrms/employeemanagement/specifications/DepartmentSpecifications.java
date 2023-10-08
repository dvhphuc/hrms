package com.hrms.employeemanagement.specifications;

import com.hrms.employeemanagement.models.Department;
import org.springframework.data.jpa.domain.Specification;

public class DepartmentSpecifications {
    public static Specification<Department> hasId(int id) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("id"), id);
    }
}
