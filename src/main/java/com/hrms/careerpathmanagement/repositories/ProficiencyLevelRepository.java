package com.hrms.careerpathmanagement.repositories;

import com.hrms.careerpathmanagement.models.ProficiencyLevel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ProficiencyLevelRepository extends JpaRepository<ProficiencyLevel, Integer>, JpaSpecificationExecutor<ProficiencyLevel> {
}
