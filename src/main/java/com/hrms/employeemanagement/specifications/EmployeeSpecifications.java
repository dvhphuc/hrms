package com.hrms.employeemanagement.specifications;

import com.hrms.employeemanagement.models.Employee;
import org.springframework.data.jpa.domain.Specification;

public class EmployeeSpecifications {
    public static Specification<Employee> hasId(int id) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("id"), id);
    }

    public static Specification<Employee> hasUnitId(int unitId) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("unit").get("id"), unitId);
    }

}
