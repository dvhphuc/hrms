package com.hrms.damservice.repositories;

import com.hrms.damservice.models.SourceFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface SourceFileRepository extends JpaRepository<SourceFile, Integer>, JpaSpecificationExecutor<SourceFile> {
}
