package com.hrms.employeecompetency.services.impl;

import com.hrms.employeecompetency.models.Competency;
import com.hrms.employeecompetency.repositories.CompetencyRepository;
import com.hrms.employeecompetency.services.CompetencyService;
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
public class CompetencyServiceImpl implements CompetencyService {
    @Autowired
    private CompetencyRepository competencyRepository;
    @Override
    public List<Competency> findAll(Specification<Competency> spec) {
        return competencyRepository.findAll(spec);
    }

    @Override
    public List<Competency> findAll(Specification<Competency> spec, Sort sort) {
        return competencyRepository.findAll(spec, sort);
    }

    @Override
    public Page<Competency> findAll(Specification<Competency> spec, Pageable pageable) {
        return competencyRepository.findAll(spec, pageable);
    }
}
