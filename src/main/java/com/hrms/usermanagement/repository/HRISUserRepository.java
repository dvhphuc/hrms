package com.hrms.usermanagement.repository;

import com.hrms.usermanagement.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface HRISUserRepository extends JpaRepository<User, Long> {
    User findByUsername(@Param("username") String username);
}
