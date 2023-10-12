package com.hrms.employeecompetency.services.impl;

import com.hrms.employeecompetency.models.CompetencyTimeLine;
import com.hrms.employeecompetency.repositories.CompetencyTimeLineRepository;
import com.hrms.employeecompetency.services.CompetencyTimeLineService;
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
public class CompetencyTimeLineServiceImpl implements CompetencyTimeLineService{
    @Autowired
    private CompetencyTimeLineRepository competencyTimeLineRepository;

    @Override
    public List<CompetencyTimeLine> findAll(Specification<CompetencyTimeLine> spec) {
        return competencyTimeLineRepository.findAll(spec);
    }

    @Override
    public List<CompetencyTimeLine> findAll(Specification<CompetencyTimeLine> spec, Sort sort) {
        return competencyTimeLineRepository.findAll(spec, sort);
    }

    @Override
    public Page<CompetencyTimeLine> findAll(Specification<CompetencyTimeLine> spec, Pageable pageable) {
        return competencyTimeLineRepository.findAll(spec, pageable);
    }
}
