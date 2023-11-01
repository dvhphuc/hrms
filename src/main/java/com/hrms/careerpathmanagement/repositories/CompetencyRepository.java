package com.hrms.careerpathmanagement.repositories;

import com.hrms.careerpathmanagement.models.Competency;
import com.hrms.careerpathmanagement.projection.CompetencyProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface CompetencyRepository extends JpaRepository<Competency, Integer>, JpaSpecificationExecutor<Competency> {
    List<CompetencyProjection> findAllBy();

}
