package com.hrms.employeecompetency.specifications;

import com.hrms.employeemanagement.models.Employee;
import org.springframework.data.jpa.domain.Specification;

public class EmployeeSpecifications {
    public static Specification<Employee> hasEmployeeId(int employeeId) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("employee").get("id"), employeeId);
    }

    public static Specification<Employee> isDepartmentId(int departmentId) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("employee").get("department").get("id"), departmentId);
    }
}
