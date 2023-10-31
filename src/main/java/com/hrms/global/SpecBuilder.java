package com.hrms.global;

import org.springframework.data.jpa.domain.Specification;

public class SpecBuilder<T> {
    public static <T> Specification<T> buildEqualSpec(String filter, String value) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get(filter), value);
    }
}
