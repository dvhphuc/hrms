package com.hrms.imagemanagement.specifications;

import com.hrms.imagemanagement.models.ImageSource;
import org.springframework.data.jpa.domain.Specification;

public class ImageSourceSpecifications {
    public static Specification<ImageSource> hasImagePath(String imagePath) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("imagePath"), imagePath);
    }

    public static Specification<ImageSource> hasEmployeeId(Integer id) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("employee").get("id"), id);
    }
}
