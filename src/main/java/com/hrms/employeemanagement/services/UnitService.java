package com.hrms.employeemanagement.services;


import com.hrms.employeemanagement.models.Department;

import java.util.Optional;

public interface UnitService {
    Iterable<Department> getAllUnits();
    Department saveUnit(Department role);
    Optional<Department> getUnitById(int id);
    Optional<Department> updateUnit(int id, Department role);
    void deleteUnitById(int id);
}
