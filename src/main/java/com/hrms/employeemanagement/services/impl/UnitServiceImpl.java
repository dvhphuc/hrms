package com.hrms.employeemanagement.services.impl;

import com.hrms.employeemanagement.exception.UnitNotFoundException;
import com.hrms.employeemanagement.models.Department;
import com.hrms.employeemanagement.repositories.UnitRepository;
import com.hrms.employeemanagement.services.UnitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class UnitServiceImpl implements UnitService {
    @Autowired
    private UnitRepository unitRepository;

    @Override
    public Iterable<Department> getAllUnits() {
        return unitRepository.findAll();
    }

    @Override
    public Department saveUnit(Department department) {
        return unitRepository.save(department);
    }

    @Override
    public Optional<Department> getUnitById(int id) {
        Optional<Department> unit = unitRepository.findById(id);
        if (unit.isEmpty()) {
            throw new UnitNotFoundException("Unit not found for id :: " + id);
        }
        return unit;
    }

    @Override
    public Optional<Department> updateUnit(int id, Department department) {
        Optional<Department> unitOptional = unitRepository.findById(id);
        if (unitOptional.isEmpty())
            throw new UnitNotFoundException("Unit not found for id :: " + id);
        department.setId(id);
        unitRepository.save(department);
        return unitOptional;
    }

    @Override
    public void deleteUnitById(int id) {
        unitRepository.deleteById(id);
    }
}
