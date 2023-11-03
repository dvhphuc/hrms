//package com.hrms.damservice.controllers;
//
//import com.hrms.damservice.models.SourceFile;
//import com.hrms.employeemanagement.models.Employee;
//import com.hrms.employeemanagement.repositories.EmployeeRepository;
//import com.hrms.employeemanagement.services.EmployeeManagementService;
//import com.hrms.damservice.services.DamService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.core.io.Resource;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.multipart.MultipartFile;
//
//@RestController
//@RequestMapping("/dam")
//public class DamController {
//    EmployeeManagementService employeeManagementService;
//    DamService damService;
//
//    EmployeeRepository employeeRepository;
//    @Autowired
//    public DamController(EmployeeManagementService employeeManagementService, DamService damService,
//                         EmployeeRepository employeeRepository) {
//        this.employeeManagementService = employeeManagementService;
//        this.damService = damService;
//        this.employeeRepository = employeeRepository;
//    }
//
//    @PostMapping("/upload/{id}")
//    public ResponseEntity<String> uploadProfileImage(
//            @PathVariable(value = "id") int employeeId, @RequestBody MultipartFile file){
//        try {
//            SourceFile sourceFile = damService.uploadFile(file);
//            Employee employee = employeeManagementService.findEmployee(employeeId);
//            employee.setDamId(sourceFile.getId());
//            employeeRepository.save(employee);
//            return ResponseEntity.ok("File uploaded successfully.");
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Could not upload the file.");
//        }
//    }
//
//    @GetMapping("/profile-image/{id}")
//    public ResponseEntity<Resource> getProfileImage(@PathVariable(value = "id") int id){
//        try {
//            Resource resource = damService.getFileId(id);
//            return ResponseEntity.ok()
//                    .header(HttpHeaders.CONTENT_DISPOSITION, "inline;filename=" + resource.getFilename())
//                    .contentType(MediaType.IMAGE_JPEG)  // Adjust the media type as per your image type
//                    .body(resource);
//        } catch (Exception e) {
//            return ResponseEntity.notFound().build();
//        }
//    }
//}