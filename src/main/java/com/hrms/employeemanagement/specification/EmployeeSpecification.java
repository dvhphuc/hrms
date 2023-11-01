package com.hrms.employeemanagement.specification;


import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class EmployeeSpecification {
    public <T> Specification<T> hasEmployeeId(Integer employeeId) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("employee").get("id"), employeeId);
    }

    public <T> Specification<T> hasPositionId(Integer positionId) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("employee").get("position").get("positionId"), positionId);
    }

    public <T> Specification<T> hasDepartmentId(Integer departmentId) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("employee").get("department").get("departmentId"), departmentId);
    }
}
