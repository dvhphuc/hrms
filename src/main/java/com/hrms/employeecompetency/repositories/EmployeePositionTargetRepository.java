package com.hrms.employeecompetency.repositories;

import com.hrms.employeecompetency.models.EmployeePositionTarget;
import com.hrms.employeecompetency.models.PositionLevel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface EmployeePositionTargetRepository extends JpaRepository<EmployeePositionTarget, Integer> {
    List<PositionLevel> findAllByEmployeeId(Integer employeeId);
}
