package com.hrms.employeecompetency.services.impl;

import com.hrms.employeecompetency.models.ProficiencyLevel;
import com.hrms.employeecompetency.repositories.ProficiencyLevelRepository;
import com.hrms.employeecompetency.services.ProficiencyLevelService;
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
public class ProficiencyLevelServiceImpl implements ProficiencyLevelService {
    @Autowired
    private ProficiencyLevelRepository proficiencyLevelRepository;
    @Override
    public List<ProficiencyLevel> findAll(Specification<ProficiencyLevel> spec) {
        return proficiencyLevelRepository.findAll(spec);
    }

    @Override
    public List<ProficiencyLevel> findAll(Specification<ProficiencyLevel> spec, Sort sort) {
        return proficiencyLevelRepository.findAll(spec, sort);
    }

    @Override
    public Page<ProficiencyLevel> findAll(Specification<ProficiencyLevel> spec, Pageable pageable) {
        return proficiencyLevelRepository.findAll(spec, pageable);
    }
}
