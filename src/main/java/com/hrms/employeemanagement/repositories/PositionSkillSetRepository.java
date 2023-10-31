package com.hrms.employeemanagement.repositories;

import com.hrms.employeemanagement.models.PositionSkillSet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface PositionSkillSetRepository extends JpaRepository<PositionSkillSet, Integer>, JpaSpecificationExecutor<PositionSkillSet> {
}
