package com.hrms.employeemanagement.services;

import com.hrms.employeemanagement.models.Employee;
import org.springframework.data.domain.Page;

import java.util.Optional;

public interface EmployeeService {
	Iterable<Employee> getAllEmployees();
	Employee saveEmployee(Employee employee);
	Optional<Employee> getEmployeeById(String id);
	Optional<Employee> uploadEmployee(String id, Employee employee);
	void deleteEmployeeById(String id);
	Page<Employee> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection);
	long countEmployee();
	Iterable<Employee> getNewEmployeeOfMonth();
	Optional<Employee> assignEmployeeToUnit(String id, String teamUnit);
}
