package com.hrms.employeemanagement.specifications;

import com.hrms.employeemanagement.models.Employee;
import jakarta.annotation.Nullable;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public class EmployeeSpecifications {
    public static Specification<Employee> hasId(int id) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("id"), id);
    }

    public static Specification<Employee> hasFilter(List<Integer> departmentIds, List<Integer> currentContracts,
                                                    Boolean status, String name) {
        Specification<Employee> idsFilter = Specification.where(null);
        Specification<Employee> currentContactsFilter = Specification.where(null);
        Specification<Employee> statusesFilter = Specification.where(null);
        Specification<Employee> nameFilter = Specification.where(null);

        if (departmentIds != null) {
            for (Integer id : departmentIds) {
                idsFilter = idsFilter.or((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("department").get("id"), id));
            }
        }
        if (currentContracts != null) {
            for (Integer currentContract : currentContracts) {
                currentContactsFilter = currentContactsFilter.or((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("currentContract"), currentContract));
            }
        }
        if (status != null) {
            statusesFilter = (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("user").get("isEnabled"), status);
        }
        if (name != null) {
            nameFilter = (root, query, criteriaBuilder) -> criteriaBuilder.or(
                    criteriaBuilder.like(root.get("lastName"), "%" + name + "%"),
                    criteriaBuilder.like(root.get("firstName"), "%" + name + "%")
            );
        }
        return idsFilter.and(currentContactsFilter).and(statusesFilter).and(nameFilter);
    }
}
