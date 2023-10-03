package com.hrms.employeemanagement.services;


import com.hrms.employeemanagement.models.Unit;

import java.util.Optional;

public interface UnitService {
    Iterable<Unit> getAllUnits();
    Unit saveUnit(Unit role);
    Optional<Unit> getUnitById(int id);
    Optional<Unit> updateUnit(int id, Unit role);
    void deleteUnitById(int id);
}
