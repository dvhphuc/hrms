package com.hrms.employeemanagement.services;


import com.hrms.employeemanagement.models.Position;

import java.util.Optional;

public interface PositionService {
    Iterable<Position> getAllRoles();
    Position saveRole(Position role);
    Optional<Position> getRoleById(int id);
    Optional<Position> updateRole(int id, Position role);
    void deleteRoleById(int id);
}
