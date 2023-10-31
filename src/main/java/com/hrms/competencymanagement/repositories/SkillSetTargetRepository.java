package com.hrms.competencymanagement.repositories;

import com.hrms.competencymanagement.models.SkillSetTarget;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface SkillSetTargetRepository extends JpaRepository<SkillSetTarget, Integer>, JpaSpecificationExecutor<SkillSetTarget> {
}
