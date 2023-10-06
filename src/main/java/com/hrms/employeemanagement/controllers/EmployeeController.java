package com.hrms.employeemanagement.controllers;

import com.hrms.employeemanagement.exception.EmployeeNotFoundException;
import com.hrms.employeemanagement.models.Employee;
import com.hrms.employeemanagement.services.EmployeeService;
import com.hrms.employeemanagement.specifications.EmployeeSpecifications;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    @Value("${file.upload-dir}")
    private String uploadDir;

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
            if (employee == null) {
                throw new EmployeeNotFoundException("id-" + id);
            }
            employee.add(
                    linkTo(methodOn(EmployeeController.class).getEmployeeByID(id)).withSelfRel());
        }
        return ResponseEntity.ok(employees);
    }

    @PostMapping("/{id}/upload")
    public ResponseEntity<String> uploadImage(
            @PathVariable(value = "id") int id, @RequestParam("file") MultipartFile file){
        try {
            String fileName = id + "_" + file.getOriginalFilename();
            Path filePath = Paths.get(uploadDir, fileName);
            if (!Files.exists(filePath.getParent())) Files.createDirectories(filePath.getParent());
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            Employee employee = employeeService.findAll(EmployeeSpecifications.hasId(id)).get(0);
            employee.setProfilePicture(fileName);
            employeeService.saveEmployee(employee);
            return ResponseEntity.ok("File uploaded successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Could not upload the file.");
        }
    }

    @GetMapping("/{id}/image")
    public ResponseEntity<byte[]> getImage(@PathVariable int id) {
        Employee employee = employeeService.findAll(EmployeeSpecifications.hasId(id)).get(0);
        String imagePath = employee.getProfilePicture();
        Path filePath = Paths.get(uploadDir, imagePath);

        try {
            byte[] imageBytes = Files.readAllBytes(filePath);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_JPEG); // Set appropriate content type
            headers.setContentDispositionFormData("attachment", imagePath);

            return new ResponseEntity<>(imageBytes, headers, HttpStatus.OK);
        } catch (IOException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/unit/{unitId}")
    public ResponseEntity<Page<Employee>> getEmployeeByUnitId(@PathVariable(value = "unitId") int unitId
            , @RequestParam(value = "pageNo", defaultValue = "1") int pageNo
            , @RequestParam(value = "pageSize", defaultValue = "5") int pageSize) {
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