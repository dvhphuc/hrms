package com.hrms.employeecompetency.services;

import com.hrms.employeecompetency.models.SkillSetTarget;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public interface SkillSetTargetService {
    List<SkillSetTarget> findAll(Specification<SkillSetTarget> spec);
    List<SkillSetTarget> findAll(Specification<SkillSetTarget> spec, Sort sort);
    Page<SkillSetTarget> findAll(Specification<SkillSetTarget> spec, Pageable pageable);
}
