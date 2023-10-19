package com.hrms.employeecompetency.services.impl;

import com.hrms.employeecompetency.models.SkillSetTarget;
import com.hrms.employeecompetency.repositories.SkillSetTargetRepository;
import com.hrms.employeecompetency.services.SkillSetTargetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class SkillSetTargetServiceImpl implements SkillSetTargetService {
    @Autowired
    SkillSetTargetRepository skillSetTargetRepository;
    @Override
    public List<SkillSetTarget> findAll(Specification<SkillSetTarget> spec) {
        return skillSetTargetRepository.findAll(spec);
    }

    @Override
    public List<SkillSetTarget> findAll(Specification<SkillSetTarget> spec, Sort sort) {
        return skillSetTargetRepository.findAll(spec, sort);
    }

    @Override
    public Page<SkillSetTarget> findAll(Specification<SkillSetTarget> spec, Pageable pageable) {
        return skillSetTargetRepository.findAll(spec, pageable);
    }
}
