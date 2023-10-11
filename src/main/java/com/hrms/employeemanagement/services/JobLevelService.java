package com.hrms.employeemanagement.services;

import com.hrms.employeecompetency.models.JobLevel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public interface JobLevelService {
    List<JobLevel> findAll(Specification<JobLevel> spec);
    List<JobLevel> findAll(Specification<JobLevel> spec, Sort sort);
    Page<JobLevel> findAll(Specification<JobLevel> spec, Pageable pageable);
}
