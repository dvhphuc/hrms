package com.hrms.careerpathmanagement.repositories;

import com.hrms.careerpathmanagement.models.SkillSetTarget;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface SkillSetTargetRepository extends JpaRepository<SkillSetTarget, Integer>, JpaSpecificationExecutor<SkillSetTarget> {
}
