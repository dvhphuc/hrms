package com.hrms.usermanagement.repository;

import com.hrms.usermanagement.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
}
