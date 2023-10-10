package com.hrms.employeemanagement.services.impl;

import com.hrms.employeemanagement.models.Position;
import com.hrms.employeemanagement.repositories.PositionRepository;
import com.hrms.employeemanagement.services.PositionService;
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
public class PositionServiceImpl implements PositionService {
    @Autowired
    private PositionRepository positionRepository;


    @Override
    public List<Position> findAll(Specification<Position> spec) {
        return positionRepository.findAll(spec);
    }

    @Override
    public List<Position> findAll(Specification<Position> spec, Sort sort) {
        return positionRepository.findAll(spec, sort);
    }

    @Override
    public Page<Position> findAll(Specification<Position> spec, Pageable pageable) {
        return positionRepository.findAll(spec, pageable);
    }
}
