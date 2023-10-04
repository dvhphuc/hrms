package com.hrms.employeemanagement.services;

import com.hrms.employeemanagement.models.Employee;
import org.springframework.data.domain.Page;

import java.util.Optional;

public interface EmployeeService {
	Iterable<Employee> getAllEmployees();
	Employee saveEmployee(Employee employee);
	Optional<Employee> getEmployeeById(int id);
	Optional<Employee> updateEmployee(int id, Employee employee);
	void deleteEmployeeById(int id);
	Page<Employee> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection);
	long countEmployee();
	Iterable<Employee> getNewEmployeeOfMonth();
	Optional<Employee> assignEmployeeToUnit(int id, int teamUnit);
}
