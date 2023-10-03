package com.hrms.employeemanagement.services.impl;

import com.hrms.employeemanagement.exception.UnitNotFoundException;
import com.hrms.employeemanagement.models.Unit;
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
    public Iterable<Unit> getAllUnits() {
        return unitRepository.findAll();
    }

    @Override
    public Unit saveUnit(Unit unit) {
        return unitRepository.save(unit);
    }

    @Override
    public Optional<Unit> getUnitById(int id) {
        Optional<Unit> unit = unitRepository.findById(id);
        if (unit.isEmpty()) {
            throw new UnitNotFoundException("Unit not found for id :: " + id);
        }
        return unit;
    }

    @Override
    public Optional<Unit> updateUnit(int id, Unit unit) {
        Optional<Unit> unitOptional = unitRepository.findById(id);
        if (unitOptional.isEmpty())
            throw new UnitNotFoundException("Unit not found for id :: " + id);
        unit.setId(id);
        unitRepository.save(unit);
        return unitOptional;
    }

    @Override
    public void deleteUnitById(int id) {
        unitRepository.deleteById(id);
    }
}
