package com.hrms.competencymanagement.repositories;

import com.hrms.competencymanagement.models.Competency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CompetencyRepository extends JpaRepository<Competency, Integer>, JpaSpecificationExecutor<Competency> {
}
