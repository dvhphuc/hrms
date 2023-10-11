package com.hrms.employeemanagement.services.impl;

import com.hrms.employeecompetency.models.PositionLevel;
import com.hrms.employeemanagement.repositories.PositionLevelRepository;
import com.hrms.employeemanagement.services.PositionLevelService;
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
public class PositionLevelServiceImpl implements PositionLevelService {
    @Autowired
    private PositionLevelRepository positionLevelRepository;
    @Override
    public List<PositionLevel> findAll(Specification<PositionLevel> spec) {
        return positionLevelRepository.findAll(spec);
    }

    @Override
    public List<PositionLevel> findAll(Specification<PositionLevel> spec, Sort sort) {
        return positionLevelRepository.findAll(spec, sort);
    }

    @Override
    public Page<PositionLevel> findAll(Specification<PositionLevel> spec, Pageable pageable) {
        return positionLevelRepository.findAll(spec, pageable);
    }
}
