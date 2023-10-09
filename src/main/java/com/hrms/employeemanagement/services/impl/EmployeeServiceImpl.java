package com.hrms.employeemanagement.services.impl;
import com.hrms.employeemanagement.exception.EmployeeNotFoundException;
import com.hrms.employeemanagement.exception.UnitNotFoundException;
import com.hrms.employeemanagement.models.Employee;
import com.hrms.employeemanagement.models.Department;
import com.hrms.employeemanagement.models.User;
import com.hrms.employeemanagement.repositories.EmployeeRepository;
import com.hrms.employeemanagement.repositories.UnitRepository;
import com.hrms.employeemanagement.services.EmployeeService;
import com.hrms.usermanagement.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class EmployeeServiceImpl implements EmployeeService {

	@Autowired
	private EmployeeRepository employeeRepository;
	@Autowired
	private UnitRepository unitRepository;

	@Override
	public List<Employee> findAll(Specification<Employee> spec) {
		return employeeRepository.findAll(spec);
	}

	@Override
	public List<Employee> findAll(Specification<Employee> spec, Sort sort) {
		return employeeRepository.findAll(spec, sort);
	}

	@Override
	public Page<Employee> findAll(Specification<Employee> spec, Pageable pageable) {
		return employeeRepository.findAll(spec, pageable);
	}

	@Override
	public Employee saveEmployee(Employee employee) {
		return this.employeeRepository.save(employee);
	}

	@Override
	public void updateEmployee(int id, Employee employee) {
		Optional<Employee> employeeOptional = employeeRepository.findById(id);
		if (employeeOptional.isEmpty())
			throw new EmployeeNotFoundException(" Employee not found for id :: " + id);
		employee.setId(id);
		employeeRepository.save(employee);
	}


	@Override
	public void deleteEmployeeById(int id) {
		this.employeeRepository.deleteById(id);
	}

	@Override
	public long countEmployee() {
		return employeeRepository.count(Specification.allOf());
	}

	@Override
	public Iterable<Employee> getNewEmployeeOfMonth() {
		return employeeRepository.findNewEmployeeOfMonth();
	}

	@Override
	public void assignEmployeeToUnit(int id, int unitId) {
		Optional<Employee> employeeOp = employeeRepository.findById(id);
		if (employeeOp.isEmpty()) {
			throw new EmployeeNotFoundException("Employee not found for id :: " + id);
		}
		Optional<Department> unit = unitRepository.findById(unitId);
		if (unit.isEmpty()) {
			throw new UnitNotFoundException("Unit not found for id :: " + unitId);
		}
		employeeOp.get().setDepartment(unit.get());
		employeeRepository.save(employeeOp.get());
	}

	@Override
	public Page<Employee> getAllByFilter(List<Integer> ids, List<Integer> currentContracts, List<Boolean> statuses, Pageable pageable) {
		Specification<Employee> idsFilter = Specification.where(null);
		Specification<Employee> currentContactsFilter = Specification.where(null);
		Specification<Employee> statusesFilter = Specification.where(null);

		if (ids != null) {
			for (Integer id : ids) {
				idsFilter = idsFilter.or((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("department").get("id"), id));
			}
		}

		if (currentContracts != null) {
			for (Integer currentContract : currentContracts) {
				currentContactsFilter = currentContactsFilter.or((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("currentContract"), currentContract));
			}
		}

		if (statuses != null) {
			for (Boolean status : statuses) {
				statusesFilter = statusesFilter.or((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("user").get("isEnabled"), status));
			}
		}

		return employeeRepository
				.findAll(idsFilter.and(currentContactsFilter).and(statusesFilter), pageable);
	}

}
