package com.hrms.employeemanagement.services.impl;

import com.hrms.employeemanagement.exception.RoleNotFoundException;
import com.hrms.employeemanagement.models.CompanyRole;
import com.hrms.employeemanagement.repositories.CompanyRoleRepository;
import com.hrms.employeemanagement.services.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class CompanyRoleServiceImpl implements RoleService {
    @Autowired
    private CompanyRoleRepository companyRoleRepository;

    @Override
    public Iterable<CompanyRole> getAllRoles() {
        return companyRoleRepository.findAll();
    }

    @Override
    public CompanyRole saveRole(CompanyRole role) {
        return companyRoleRepository.save(role);
    }

    @Override
    public Optional<CompanyRole> getRoleById(int id) {
        Optional<CompanyRole> role = companyRoleRepository.findById(id);
        if (role.isEmpty()) {
            throw new RoleNotFoundException("Role not found for id :: " + id);
        }
        return role;
    }

    @Override
    public Optional<CompanyRole> updateRole(int id, CompanyRole role) {
        Optional<CompanyRole> roleOptional = companyRoleRepository.findById(id);
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
