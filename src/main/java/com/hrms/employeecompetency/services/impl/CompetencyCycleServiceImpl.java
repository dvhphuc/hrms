package com.hrms.employeecompetency.services.impl;

import com.hrms.employeecompetency.models.CompetencyCycle;
import com.hrms.employeecompetency.repositories.CompetencyCycleRepository;
import com.hrms.employeecompetency.services.CompetencyCycleService;
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
public class CompetencyCycleServiceImpl implements CompetencyCycleService {
    @Autowired
    CompetencyCycleRepository competencyCycleRepository;
    @Override
    public List<CompetencyCycle> findAll(Specification<CompetencyCycle> spec) {
        return competencyCycleRepository.findAll(spec);
    }

    @Override
    public List<CompetencyCycle> findAll(Specification<CompetencyCycle> spec, Sort sort) {
        return competencyCycleRepository.findAll(spec, sort);
    }

    @Override
    public Page<CompetencyCycle> findAll(Specification<CompetencyCycle> spec, Pageable pageable) {
        return competencyCycleRepository.findAll(spec, pageable);
    }

    @Override
    public CompetencyCycle getLatestCompetencyCycle() {
        return competencyCycleRepository.findFirstByOrderByStartDateDesc();
    }
}
