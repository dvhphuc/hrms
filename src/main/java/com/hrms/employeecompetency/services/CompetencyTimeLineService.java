package com.hrms.employeecompetency.services;

import com.hrms.employeecompetency.models.CompetencyTimeLine;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public interface CompetencyTimeLineService {
    List<CompetencyTimeLine> findAll(Specification<CompetencyTimeLine> spec);
    List<CompetencyTimeLine> findAll(Specification<CompetencyTimeLine> spec, Sort sort);
    Page<CompetencyTimeLine> findAll(Specification<CompetencyTimeLine> spec, Pageable pageable);
}
