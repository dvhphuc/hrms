package com.hrms.employeemanagement.controllers;

import com.hrms.employeemanagement.models.Employee;
import com.hrms.employeemanagement.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class EmployeeController {

	@Autowired
	private EmployeeService employeeService;

	@QueryMapping
	public List<Employee> findAllEmployees() {
		return employeeService.getAllEmployees();
	}
	@QueryMapping
	public long countEmployees() {
		return employeeService.countEmployee();
	}
	@QueryMapping
	public Employee findEmployeeById(@Argument String id) {
		return employeeService.getEmployeeById(id);
	}
	@QueryMapping
	public Employee findEmployeeByEmail(@Argument String email) {
		return employeeService.getEmployeeByEmail(email);
	}
	@MutationMapping
	public Employee newEmployee(@Argument String firstName, @Argument String lastName,
								@Argument String email, @Argument String gender, @Argument String dateOfBirth,
								@Argument String phoneNumber, @Argument String address, @Argument String positionLevel){
		Employee employee = new Employee();
		employee.setFirstName(firstName);
		employee.setLastName(lastName);
		employee.setEmail(email);
		employee.setGender(gender);
		employee.setDateOfBirth(dateOfBirth);
		employee.setPhoneNumber(phoneNumber);
		employee.setAddress(address);
		employee.setPositionLevel(positionLevel);
		employeeService.saveEmployee(employee);
		return employee;
	}
	@MutationMapping
	public boolean deleteEmployee(@Argument String id) {
		employeeService.deleteEmployeeById(id);
		return true;
	}
	@MutationMapping
	public Employee updateEmployee(@Argument String id, @Argument String firstName, @Argument String lastName,
								   @Argument String email, @Argument String gender, @Argument String dateOfBirth,
								   @Argument String phoneNumber, @Argument String address, @Argument String positionLevel) {
		Employee employee = new Employee();
		employee.setId(id);
		employee.setFirstName(firstName);
		employee.setLastName(lastName);
		employee.setEmail(email);
		employee.setGender(gender);
		employee.setDateOfBirth(dateOfBirth);
		employee.setPhoneNumber(phoneNumber);
		employee.setAddress(address);
		employee.setPositionLevel(positionLevel);
		employeeService.saveEmployee(employee);
		return employee;
	}

	// display list of employees
	@GetMapping("/")
	public String viewHomePage(Model model) {
		return findPaginated(1, "firstName", "asc", model);		
	}
	
	@GetMapping("/showNewEmployeeForm")
	public String showNewEmployeeForm(Model model) {
		// create model attribute to bind form data
		Employee employee = new Employee();
		model.addAttribute("employee", employee);
		return "new_employee";
	}
	
	@PostMapping("/saveEmployee")
	public String saveEmployee(@ModelAttribute("employee") Employee employee) {
		employeeService.saveEmployee(employee);
		return "redirect:/";
	}
	
	@GetMapping("/showFormForUpdate/{id}")
	public String showFormForUpdate(@PathVariable ( value = "id") String id, Model model) {
		
		// get employee from the service
		Employee employee = employeeService.getEmployeeById(id);
		
		// set employee as a model attribute to pre-populate the form
		model.addAttribute("employee", employee);
		return "update_employee";
	}
	
	@GetMapping("/deleteEmployee/{id}")
	public String deleteEmployeeByID(@PathVariable (value = "id") String id) {
		
		// call delete employee method 
		this.employeeService.deleteEmployeeById(id);
		return "redirect:/";
	}
	
	
	@GetMapping("/page/{pageNo}")
	public String findPaginated(@PathVariable (value = "pageNo") int pageNo, 
			@RequestParam("sortField") String sortField,
			@RequestParam("sortDir") String sortDir,
			Model model) {
		int pageSize = 5;
		
		Page<Employee> page = employeeService.findPaginated(pageNo, pageSize, sortField, sortDir);
		List<Employee> listEmployees = page.getContent();
		
		model.addAttribute("currentPage", pageNo);
		model.addAttribute("totalPages", page.getTotalPages());
		model.addAttribute("totalItems", page.getTotalElements());
		
		model.addAttribute("sortField", sortField);
		model.addAttribute("sortDir", sortDir);
		model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");
		
		model.addAttribute("listEmployees", listEmployees);
		return "index";
	}
}
