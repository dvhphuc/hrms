package com.hrms.employeemanagement.repositories;

import com.hrms.employeemanagement.models.EmployeeDam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface EmployeeDamRepository extends JpaRepository<EmployeeDam, Integer>, JpaSpecificationExecutor<EmployeeDam> {
}
