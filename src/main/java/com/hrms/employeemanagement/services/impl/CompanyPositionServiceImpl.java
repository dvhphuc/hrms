package com.hrms.employeemanagement.services.impl;

import com.hrms.employeemanagement.exception.RoleNotFoundException;
import com.hrms.employeemanagement.models.Position;
import com.hrms.employeemanagement.repositories.CompanyRoleRepository;
import com.hrms.employeemanagement.services.PositionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class CompanyPositionServiceImpl implements PositionService {
    @Autowired
    private CompanyRoleRepository companyRoleRepository;

    @Override
    public Iterable<Position> getAllRoles() {
        return companyRoleRepository.findAll();
    }

    @Override
    public Position saveRole(Position role) {
        return companyRoleRepository.save(role);
    }

    @Override
    public Optional<Position> getRoleById(int id) {
        Optional<Position> role = companyRoleRepository.findById(id);
        if (role.isEmpty()) {
            throw new RoleNotFoundException("Role not found for id :: " + id);
        }
        return role;
    }

    @Override
    public Optional<Position> updateRole(int id, Position role) {
        Optional<Position> roleOptional = companyRoleRepository.findById(id);
        if (roleOptional.isEmpty())
            throw new RoleNotFoundException("Role not found for id :: " + id);
        role.setId(id);
        companyRoleRepository.save(role);
        return roleOptional;
    }

    @Override
    public void deleteRoleById(int id) {
        companyRoleRepository.deleteById(id);
    }
}
