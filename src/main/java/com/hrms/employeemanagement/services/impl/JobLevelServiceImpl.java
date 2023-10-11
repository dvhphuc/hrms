package com.hrms.employeemanagement.services.impl;

import com.hrms.employeecompetency.models.JobLevel;
import com.hrms.employeemanagement.repositories.JobLevelRepository;
import com.hrms.employeemanagement.services.JobLevelService;
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
public class JobLevelServiceImpl implements JobLevelService {
    @Autowired
    JobLevelRepository jobLevelRepository;
    @Override
    public List<JobLevel> findAll(Specification<JobLevel> spec) {
        return jobLevelRepository.findAll(spec);
    }

    @Override
    public List<JobLevel> findAll(Specification<JobLevel> spec, Sort sort) {
        return jobLevelRepository.findAll(spec, sort);
    }

    @Override
    public Page<JobLevel> findAll(Specification<JobLevel> spec, Pageable pageable) {
        return jobLevelRepository.findAll(spec, pageable);
    }
}
