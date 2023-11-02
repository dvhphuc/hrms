package com.hrms.employeemanagement.services.impl;

import com.hrms.employeemanagement.dto.*;
import com.hrms.employeemanagement.models.*;
import com.hrms.global.paging.Pagination;
import com.hrms.global.paging.PagingInfo;
import com.hrms.employeemanagement.repositories.*;
import com.hrms.employeemanagement.services.EmployeeManagementService;
import com.unboundid.util.NotNull;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.*;

import static com.hrms.careerpathmanagement.controllers.CompetencyController.setupPaging;

@Service
@Transactional
public class EmployeeManagementServiceImpl implements EmployeeManagementService {
    EmployeeRepository employeeRepository;
    DepartmentRepository departmentRepository;
    @Autowired
    private EmergencyContactRepository emergencyContactRepository;
    private ModelMapper modelMapper;

    @Autowired
    public EmployeeManagementServiceImpl(EmployeeRepository employeeRepository,
                                         DepartmentRepository departmentRepository) {
        this.employeeRepository = employeeRepository;
        this.departmentRepository = departmentRepository;
    }

    @Bean
    public void setUpMapper() {
        this.modelMapper = new ModelMapper();
    }

    @Override
    public List<Employee> getAllEmployees() {
        //Find all employee have status not equal "Terminated"
        Specification<Employee> spec = (root, query, builder) -> builder.notEqual(root.get("status"), 0);
        return employeeRepository.findAll(spec);
    }

    @Override
    public Employee findEmployee(Integer id) {
        Specification<Employee> spec = ((root, query, builder) -> builder.equal(root.get("id"), id));
        return employeeRepository
                .findOne(spec)
                .orElseThrow(() -> new RuntimeException("Employee not found with id: " + id));
    }

    @Override
    public EmployeeDetail getEmployeeDetail(Integer id) {
        Specification<Employee> spec = ((root, query, builder) -> builder.equal(root.get("id"), id));
        Employee employee = employeeRepository
                .findOne(spec)
                .orElseThrow(() -> new RuntimeException("Employee not found with id: " + id));
        Specification<EmergencyContact> contactSpec = (root, query, builder)
                -> builder.equal(root.get("employee").get("id"), id);
        List<EmergencyContact> emergencyContacts = emergencyContactRepository.findAll(contactSpec);
        return new EmployeeDetail(employee, emergencyContacts);
    }

    //TODO:Input DepartmentIds
    @Override
    public List<Employee> findEmployees(List<Integer> departmentIds) {
        Specification<Employee> spec = (root, query, criteriaBuilder) -> root.get("department").get("id").in(departmentIds);
        return employeeRepository.findAll(spec);
    }

    @Override
    public List<Employee> findEmployees(Integer departmentId) {
        //have departmentId = departmentId and status not equal to 0
        Specification<Employee> spec = (root, query, criteriaBuilder) -> criteriaBuilder.and(
                criteriaBuilder.equal(root.get("department").get("id"), departmentId),
                criteriaBuilder.notEqual(root.get("status"), 0)
        );
        return employeeRepository.findAll(spec);
    }

    @Override
    public List<Employee> getNewEmployees() {
        PageRequest pageRequest = PageRequest.of(0, 10, Sort.by(Sort.Order.desc("joinedDate")));

        return employeeRepository.findAll(pageRequest).getContent();
    }

    //TODO: ADD SORTING
    @Override
    public EmployeePaging filterEmployees(List<Integer> departmentIds,
                                          List<Integer> currentContracts,
                                          Boolean status,
                                          String name,
                                          PagingInfo pagingInfo) {
        Sort sort = pagingInfo.getSortBy() != null ? Sort.by(Sort.Direction.DESC, pagingInfo.getSortBy()) : null;
        Pageable pageable = sort != null
                ? PageRequest.of(pagingInfo.getPageNo() - 1, pagingInfo.getPageSize(), sort)
                : PageRequest.of(pagingInfo.getPageNo() - 1, pagingInfo.getPageSize());
        Specification<Employee> filterSpec = (root, query, criteriaBuilder) -> criteriaBuilder.and(
                departmentIds != null ? root.get("department").get("id").in(departmentIds) : criteriaBuilder.conjunction(),
                currentContracts != null ? root.get("currentContract").in(currentContracts) : criteriaBuilder.conjunction(),
                status != null ? criteriaBuilder.equal(root.get("user").get("isEnabled"), status) : criteriaBuilder.conjunction(),
                name != null ? criteriaBuilder.or(
                        criteriaBuilder.like(root.get("lastName"), "%" + name + "%"),
                        criteriaBuilder.like(root.get("firstName"), "%" + name + "%")
                ) : criteriaBuilder.conjunction()
        );
        Page<Employee> empPage = employeeRepository.findAll(filterSpec, pageable);
        Pagination pagination = setupPaging(empPage, pagingInfo.getPageNo(), pagingInfo.getPageSize());
        return new EmployeePaging(empPage.getContent(), pagination);
    }

    //TODO: REPLACE TO YEAR
    @Override
    public Headcount getHeadcountsStatistic() {
        //Get all new employees have joinedDate between 2 years ago and 1 year ago
        LocalDate fromDatePrevious = LocalDate.now().minusYears(2);
        LocalDate toDatePrevious = LocalDate.now().minusYears(1);
        var countPreviousEmployees = countEmployeesByYear(fromDatePrevious, toDatePrevious);

        //Get all new employees have joinedDate between today and 1 year ago
        LocalDate fromDateCurrent = LocalDate.now().minusYears(1);
        LocalDate toDateCurrent = LocalDate.now();
        var countCurrentEmployees = countEmployeesByYear(fromDateCurrent, toDateCurrent);

        var countAllEmployee = getAllEmployees().size();

        float diffPercent = ((float) (countCurrentEmployees - countPreviousEmployees) / countPreviousEmployees) * 100;

        return new Headcount(countAllEmployee, diffPercent, countPreviousEmployees <= countCurrentEmployees);
    }

    @Override
    public List<HeadcountChartData> getHeadcountChartData() {
        List<Department> department = departmentRepository.findAll();
        List<Integer> departmentIds = department.stream().map(Department::getId).toList();
        //Find all employees in departmentIds and have status not equal to 0
        Specification<Employee> spec = (root, query, builder) -> builder.and(
                builder.in(root.get("department").get("id")).value(departmentIds),
                builder.notEqual(root.get("status"), 0)
        );
        List<Employee> employees = employeeRepository.findAll(spec);

        return department.stream().map(item -> {
            Integer countEmployee = Math.toIntExact(employees
                    .stream()
                    .filter(employee -> employee.getDepartment().getId() == item.getId())
                    .count());
            return new HeadcountChartData(item.getDepartmentName(), countEmployee);
        }).toList();
    }

    private long countEmployeesByYear(LocalDate fromDate, LocalDate toDate) {
        //Get all new employees have joinedDate between fromDate and toDate and have status not equal to 0
        Specification<Employee> spec = (root, query, builder) -> builder.and(
                builder.between(root.get("joinedDate"), fromDate, toDate),
                builder.notEqual(root.get("status"), 0)
        );

        return employeeRepository.count(spec);
    }

    @Override
    @Transactional
    public Employee createEmployee(EmployeeDTO employeeDTO) {
        Employee employee = new Employee();

        return updateEmployee(employeeDTO, employee);
    }

    @Override
    @Transactional
    public Employee updateEmployee(EmployeeDTO input) {
        Employee employee = findEmployee(input.getId());

        return updateEmployee(input, employee);
    }

    @NotNull
    private Employee updateEmployee(EmployeeDTO employeeDTO, Employee employee) {
        modelMapper.map(employeeDTO, employee);
        employeeRepository.save(employee);
        manageEmergencyContacts(employeeDTO.getEmergencyContacts(), employee);

        return employee;
    }

    private void manageEmergencyContacts(List<EmergencyContactInput> emergencyContacts, Employee employee) {
        deleteEmerContactNotInNewList(emergencyContacts, employee.getId());
        insertOrUpdateEmerContact(emergencyContacts, employee);
    }

    private void deleteEmerContactNotInNewList(List<EmergencyContactInput> emergencyContacts, Integer employeeId) {
        // Get all emergency contacts of the employee
        Specification<EmergencyContact> spec = (root, query, builder) -> builder.equal(root.get("employee").get("id"), employeeId);
        List<EmergencyContact> ecs = emergencyContactRepository.findAll(spec);

        // Collect emergency contacts to be deleted
        List<EmergencyContact> ecsToDelete = ecs.stream()
                .filter(ec -> emergencyContacts.stream()
                        .noneMatch(e -> e.getId() != null && e.getId().equals(ec.getId())))
                .toList();

        // Delete the collected emergency contacts
        emergencyContactRepository.deleteAll(ecsToDelete);
    }

    private void insertOrUpdateEmerContact(List<EmergencyContactInput> emergencyContacts, Employee employee) {
        List<Integer> ecIds = emergencyContacts.stream()
                .map(EmergencyContactInput::getId)
                .toList();
        Specification<EmergencyContact> spec = (root, query, builder) -> root.get("id").in(ecIds);
        List<EmergencyContact> ecs = emergencyContactRepository.findAll(spec);

        List<EmergencyContact> emergencyContactList = emergencyContacts.stream()
                .map(ec -> {
                    EmergencyContact emergencyContact = ec.getId() == null ? new EmergencyContact() :
                            ecs.stream()
                                    .filter(e -> e.getId() == ec.getId())
                                    .findFirst()
                                    .orElseThrow(() -> new RuntimeException("Emergency Contact not found with id: " + ec.getId()));
                    modelMapper.map(ec, emergencyContact);
                    emergencyContact.setEmployee(employee);
                    return emergencyContact;
                })
                .toList();

        emergencyContactRepository.saveAll(emergencyContactList);
    }
}
