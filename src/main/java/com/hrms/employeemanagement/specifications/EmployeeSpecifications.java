package com.hrms.employeemanagement.specifications;

import com.hrms.employeemanagement.models.Employee;
import jakarta.annotation.Nullable;
import org.springframework.data.jpa.domain.Specification;

public class EmployeeSpecifications {
    public static Specification<Employee> hasId(int id) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("id"), id);
    }

    public static Specification<Employee> hasDepartmentId(int departmentId) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("department").get("id"), departmentId);
    }

    public static Specification<Employee> hasPositionLevelId(int positionLevelId) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("positionLevel").get("id"), positionLevelId);
    }

    public static Specification<Employee> hasStatus(boolean isEnabled) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("isEnabled"), isEnabled);
    }

    public static Specification<Employee> hasFilter(@Nullable int departmentId,
                                                    @Nullable int positionLevelId,
                                                    @Nullable boolean isEnabled) {
        return Specification.where(hasDepartmentId(departmentId))
                .and(hasPositionLevelId(positionLevelId))
                .and(hasStatus(isEnabled));
    }
}
