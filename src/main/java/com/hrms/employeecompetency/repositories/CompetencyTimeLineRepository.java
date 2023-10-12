package com.hrms.employeecompetency.repositories;

import com.hrms.employeecompetency.models.CompetencyTimeLine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CompetencyTimeLineRepository extends JpaRepository<CompetencyTimeLine, Integer>, JpaSpecificationExecutor<CompetencyTimeLine> {
}
