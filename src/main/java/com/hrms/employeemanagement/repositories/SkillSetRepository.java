package com.hrms.employeemanagement.repositories;

import com.hrms.employeemanagement.models.SkillSet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface SkillSetRepository extends JpaRepository<SkillSet, Integer>, JpaSpecificationExecutor<SkillSet> {
}
