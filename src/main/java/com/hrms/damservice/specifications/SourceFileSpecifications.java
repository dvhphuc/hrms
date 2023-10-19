package com.hrms.damservice.specifications;

import com.hrms.damservice.models.SourceFile;
import org.springframework.data.jpa.domain.Specification;

public class SourceFileSpecifications {
    public static Specification<SourceFile> hasImagePath(String imagePath) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("filePath"), imagePath);
    }

    public static Specification<SourceFile> hasId(int id) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("id"), id);
    }
}
