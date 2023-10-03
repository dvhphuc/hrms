package com.hrms.employeemanagement.controllers;

import com.hrms.employeemanagement.exception.EmployeeNotFoundException;
import com.hrms.employeemanagement.models.Employee;
import com.hrms.employeemanagement.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

	@Autowired
	private EmployeeService employeeService;

	@GetMapping
	public CollectionModel<Employee> getAllEmployee() {
		Iterable<Employee> employees = employeeService.getAllEmployees();
		for (Employee employee : employees) {
			employee.add(
					linkTo(methodOn(EmployeeController.class).getEmployeeByID(employee.getId())).withSelfRel());
		}
		CollectionModel<Employee> collectionModel = CollectionModel.of(employees);
		collectionModel.add(linkTo(methodOn(EmployeeController.class).getAllEmployee()).withSelfRel());
		return collectionModel;
	}

	@GetMapping("/{id}")
	public HttpEntity<Employee> getEmployeeByID(@PathVariable String id) {
		Optional<Employee> employeeOptional = employeeService.getEmployeeById(id);
		if(employeeOptional.isEmpty()) {
			throw new EmployeeNotFoundException("id-" + id);
		}
		employeeOptional.get().add(
				linkTo(methodOn(EmployeeController.class).getEmployeeByID(id)).withSelfRel());
		return new ResponseEntity<>(employeeOptional.get(), HttpStatus.OK);
	}

	@GetMapping("/page/{pageNo}")
	public ResponseEntity<Page<Employee>> findPaginated(@PathVariable (value = "pageNo") int pageNo,
			@RequestParam("sortField") String sortField,
			@RequestParam("sortDir") String sortDir) {
		int pageSize = 5;

		Page<Employee> page = employeeService.findPaginated(pageNo, pageSize, sortField, sortDir);

		return new ResponseEntity<>(page, HttpStatus.OK);
	}

	@PostMapping
	public HttpEntity<Employee> createEmployee(@RequestBody Employee employee) {
		Employee savedEmployee = employeeService.saveEmployee(employee);

		employee.add(linkTo(methodOn(EmployeeController.class)
				.getEmployeeByID(savedEmployee.getId())).withSelfRel());

		employee.add(linkTo(methodOn(EmployeeController.class)
				.getAllEmployee()).withRel(IanaLinkRelations.COLLECTION));

		return ResponseEntity.created(linkTo(methodOn(EmployeeController.class)
				.getEmployeeByID(savedEmployee.getId())).toUri()).body(savedEmployee);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Employee> updateEmployee(@PathVariable String id, @RequestBody Employee employee) {
		employeeService.uploadEmployee(id, employee);
		return ResponseEntity.noContent().build();
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Employee> deleteEmployee(@PathVariable String id) {
		employeeService.deleteEmployeeById(id);
		return ResponseEntity.noContent().build();
	}

	@PutMapping("/assign/unit/{employeeId}")
	public ResponseEntity<Employee> assignUnitEmployee(@PathVariable String employeeId, @RequestBody String unitName) {
		employeeService.assignEmployeeToUnit(employeeId, unitName);
		return ResponseEntity.noContent().build();
	}

	@PutMapping("/assign/unit/{employeeId}/{projectId}")
	public ResponseEntity<Employee> assignProjectEmployee(@PathVariable String employeeId, @PathVariable String projectId) {
		return null;
	}
}
