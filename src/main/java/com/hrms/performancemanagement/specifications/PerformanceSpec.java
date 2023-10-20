package com.hrms.performancemanagement.specifications;

import com.hrms.performancemanagement.model.EmployeePerformance;
import org.springframework.data.jpa.domain.Specification;

public class PerformanceSpec {
    public static Specification<EmployeePerformance> hasEmployeeId(Integer employeeId) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("employee").get("id"), employeeId);
    }
}
