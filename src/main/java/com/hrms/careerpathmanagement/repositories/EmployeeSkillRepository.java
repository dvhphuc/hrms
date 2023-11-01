package com.hrms.careerpathmanagement.repositories;

import com.hrms.employeemanagement.models.EmployeeSkill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface EmployeeSkillRepository extends JpaRepository<EmployeeSkill, Integer>, JpaSpecificationExecutor<EmployeeSkill> {
}
