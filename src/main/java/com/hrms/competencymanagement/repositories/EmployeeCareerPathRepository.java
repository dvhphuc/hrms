package com.hrms.competencymanagement.repositories;

import com.hrms.competencymanagement.models.EmployeeCareerPath;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmployeeCareerPathRepository extends JpaRepository<EmployeeCareerPath, Integer> {
    EmployeeCareerPath findByEmployeeId(int employeeId);
    List<EmployeeCareerPath> findAllByEmployeeId(int employeeId);
}
