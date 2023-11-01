package com.hrms.careerpathmanagement.repositories;

import com.hrms.careerpathmanagement.models.NextPositionLevel;
import com.hrms.employeemanagement.models.PositionLevel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NextPositionLevelRepository extends JpaRepository<NextPositionLevel, Integer> {
    PositionLevel findFirstByCurrentId(int currentId);
}
