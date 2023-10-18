package com.hrms.damservice.specifications;

import com.hrms.damservice.models.SourceFile;
import org.springframework.data.jpa.domain.Specification;

public class SourceFileSpecifications {
    public static Specification<SourceFile> hasImagePath(String imagePath) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("imagePath"), imagePath);
    }

    public static Specification<SourceFile> hasEmployeeId(Integer id) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("employee").get("id"), id);
    }
}
