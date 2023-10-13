package com.hrms.imagemanagement.repositories;

import com.hrms.imagemanagement.models.ImageSource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ImageSourceRepository extends JpaRepository<ImageSource, Integer>, JpaSpecificationExecutor<ImageSource> {
}
