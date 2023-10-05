package com.hrms.employeemanagement.repositories;

import com.hrms.employeemanagement.models.Position;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyRoleRepository extends JpaRepository<Position, Integer> {
}
