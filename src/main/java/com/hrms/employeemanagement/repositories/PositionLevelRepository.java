package com.hrms.employeemanagement.repositories;

import com.hrms.employeemanagement.models.PositionLevel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface PositionLevelRepository extends JpaRepository<PositionLevel, Integer>, JpaSpecificationExecutor<PositionLevel> {
}
