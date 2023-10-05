package com.hrms.employeemanagement.services;

import com.hrms.employeemanagement.models.Employee;
import jakarta.annotation.Nullable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public interface EmployeeService {
	List<Employee> findAll(Specification<Employee> spec);
	List<Employee> findAll(Specification<Employee> spec, Sort sort);
	Page<Employee> findAll(Specification<Employee> spec, Pageable pageable);
	Employee saveEmployee(Employee employee);
	void updateEmployee(int id, Employee employee);
	void deleteEmployeeById(int id);
	long countEmployee();
	Iterable<Employee> getNewEmployeeOfMonth();
	void assignEmployeeToUnit(int id, int teamUnit);
}
