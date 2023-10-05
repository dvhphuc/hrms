package com.hrms.employeemanagement.controllers;

import com.hrms.employeemanagement.exception.EmployeeNotFoundException;
import com.hrms.employeemanagement.models.Employee;
import com.hrms.employeemanagement.services.EmployeeService;
import com.hrms.employeemanagement.specifications.EmployeeSpecifications;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

	@Autowired
	private EmployeeService employeeService;

	@GetMapping
	public ResponseEntity<Page<Employee>> getEmployeesPaging(
			@RequestParam(value = "pageNo", defaultValue = "1") int pageNo,
			@RequestParam(value = "pageSize", defaultValue = "5") int pageSize) {
		Page<Employee> employees = employeeService.findAll(Specification.allOf()
				, PageRequest.of(pageNo - 1, pageSize));
		employees.map(employee -> employee.add(
				linkTo(methodOn(EmployeeController.class).getEmployeeByID(employee.getId())).withSelfRel()));
		return ResponseEntity.ok(employees);
	}

	@GetMapping("/{id}")
	public ResponseEntity<List<Employee>> getEmployeeByID(@PathVariable(value = "id") int id) {
		List<Employee> employees = employeeService.findAll(EmployeeSpecifications.hasId(id));
		for (Employee employee : employees) {
			if(employee == null) {
				throw new EmployeeNotFoundException("id-" + id);
			}
			employee.add(
					linkTo(methodOn(EmployeeController.class).getEmployeeByID(id)).withSelfRel());
		}
		return ResponseEntity.ok(employees);
	}

	@GetMapping("/unit/{unitId}")
	public ResponseEntity<Page<Employee>> getEmployeeByUnitId(@PathVariable(value = "unitId") int unitId
	, @RequestParam(value = "pageNo", defaultValue = "1") int pageNo
			,@RequestParam(value = "pageSize", defaultValue = "5") int pageSize) {
		Page<Employee> employees = employeeService.findAll(EmployeeSpecifications.hasUnitId(unitId)
				, PageRequest.of(pageNo - 1, pageSize));
		employees.map(employee -> employee.add(
				linkTo(methodOn(EmployeeController.class).getEmployeeByID(employee.getId())).withSelfRel()));
		return ResponseEntity.ok(employees);
	}

	@PostMapping
	public ResponseEntity<Employee> createEmployee(@RequestBody Employee employee) {
		Employee savedEmployee = employeeService.saveEmployee(employee);

		employee.add(linkTo(methodOn(EmployeeController.class)
				.getEmployeeByID(savedEmployee.getId())).withSelfRel());

		return ResponseEntity.created(linkTo(methodOn(EmployeeController.class)
				.getEmployeeByID(savedEmployee.getId())).toUri()).body(savedEmployee);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Void> updateEmployee(@PathVariable int id, @RequestBody Employee employee) {
		employeeService.updateEmployee(id, employee);
		return ResponseEntity.noContent().build();
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteEmployee(@PathVariable int id) {
		employeeService.deleteEmployeeById(id);
		return ResponseEntity.noContent().build();
	}

	@PutMapping("/assign/unit")
	public ResponseEntity<Void> assignUnitEmployee(@RequestParam int employeeId, @RequestParam int unitId) {
		employeeService.assignEmployeeToUnit(employeeId, unitId);
		return ResponseEntity.noContent().build();
	}
}