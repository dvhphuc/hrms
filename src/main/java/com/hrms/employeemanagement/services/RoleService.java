package com.hrms.employeemanagement.services;


import com.hrms.employeemanagement.models.CompanyRole;

import java.util.Optional;

public interface RoleService {
    Iterable<CompanyRole> getAllRoles();
    CompanyRole saveRole(CompanyRole role);
    Optional<CompanyRole> getRoleById(int id);
    Optional<CompanyRole> updateRole(int id, CompanyRole role);
    void deleteRoleById(int id);
}
