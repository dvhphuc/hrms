package com.hrms.employeemanagement.repositories;

import com.hrms.employeemanagement.models.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeProjectRepository extends JpaRepository<Employee, String> {
}
