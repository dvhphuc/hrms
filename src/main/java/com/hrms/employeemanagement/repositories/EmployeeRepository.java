package com.hrms.employeemanagement.repositories;

import com.hrms.employeemanagement.models.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
    @Query(value = "SELECT * FROM employee " +
            "WHERE STR_TO_DATE(datejoined, '%Y-%m-%d') >= DATE_SUB(NOW(), INTERVAL 1 MONTH)", nativeQuery = true)
    Iterable<Employee> findNewEmployeeOfMonth();

}
