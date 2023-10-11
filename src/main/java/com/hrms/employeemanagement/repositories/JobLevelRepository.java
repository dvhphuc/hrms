package com.hrms.employeemanagement.repositories;

import com.hrms.employeecompetency.models.JobLevel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface JobLevelRepository extends JpaRepository<JobLevel, Integer>, JpaSpecificationExecutor<JobLevel> {
}
