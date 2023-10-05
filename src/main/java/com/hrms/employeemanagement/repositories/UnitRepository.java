package com.hrms.employeemanagement.repositories;

import com.hrms.employeemanagement.models.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UnitRepository extends JpaRepository<Department, Integer> {
}
