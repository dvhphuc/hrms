package com.hrms.employeemanagement.services.impl;
import com.hrms.employeemanagement.models.Employee;
import com.hrms.employeemanagement.repositories.EmployeeRepository;
import com.hrms.employeemanagement.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class EmployeeServiceImpl implements EmployeeService {

	@Autowired
	private EmployeeRepository employeeRepository;

	@Override
	public Iterable<Employee> getAllEmployees() {
		return employeeRepository.findAll();
	}

	@Override
	public Employee saveEmployee(Employee employee) {
		return this.employeeRepository.save(employee);
	}

	@Override
	public Optional<Employee> getEmployeeById(String id) {
		Optional<Employee> optional = employeeRepository.findById(id);
		if (optional.isEmpty()) {
			throw new RuntimeException(" Employee not found for id :: " + id);
		}
		return optional;
	}

	@Override
	public Optional<Employee> uploadEmployee(String id, Employee employee) {
		Optional<Employee> employeeOptional = employeeRepository.findById(id);
		if (employeeOptional.isEmpty())
			throw new RuntimeException(" Employee not found for id :: " + id);
		employee.setId(id);
		employeeRepository.save(employee);
		return employeeOptional;
	}


	@Override
	public void deleteEmployeeById(String id) {
		this.employeeRepository.deleteById(id);
	}

	@Override
	public Page<Employee> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection) {
		Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() :
			Sort.by(sortField).descending();
		
		Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);
		return this.employeeRepository.findAll(pageable);
	}

	@Override
	public long countEmployee() {
		return employeeRepository.count();
	}

	@Override
	public Iterable<Employee> getNewEmployeeOfMonth() {
		return employeeRepository.findNewEmployeeOfMonth();
	}

	@Override
	public Optional<Employee> assignEmployeeToUnit(String id, String teamUnit) {
		Optional<Employee> employeeOp = employeeRepository.findById(id);
		if (employeeOp.isEmpty()) {
			throw new RuntimeException(" Employee not found for id :: " + id);
		}
		employeeOp.get().setTeamUnit(teamUnit);
		employeeRepository.save(employeeOp.get());
		return employeeOp;
	}

}
