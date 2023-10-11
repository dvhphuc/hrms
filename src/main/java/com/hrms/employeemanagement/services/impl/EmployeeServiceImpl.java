package com.hrms.employeemanagement.services.impl;

import com.hrms.employeemanagement.models.Employee;
import com.hrms.employeemanagement.repositories.EmployeeRepository;
import com.hrms.employeemanagement.services.EmployeeService;
import com.hrms.employeemanagement.specifications.EmployeeSpecifications;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

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
    public Iterable<Employee> getNewEmployeeOfMonth() {
        return employeeRepository.findNewEmployeeOfMonth();
    }

    @Override
    public Page<Employee> getAllByFilter(List<Integer> departmentIds, List<Integer> currentContracts,
                                         Boolean status, String name, Pageable pageable) {
        return employeeRepository
                .findAll(EmployeeSpecifications.hasFilter(departmentIds, currentContracts, status, name), pageable);
    }
}
