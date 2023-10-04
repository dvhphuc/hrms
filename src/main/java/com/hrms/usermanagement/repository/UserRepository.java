package com.hrms.usermanagement.repository;

import com.hrms.usermanagement.model.User;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(@Param("username") String username);

    List<User> findAll(Sort sort);

    @Query("SELECT u FROM User u WHERE u.role.name = :role order by u.createdAt desc")
    List<User> findAllByRole(String role, Sort sort);

    Page<User> findAllByRole(String role, Pageable pageable);
    Page<User> findAllByIsEnabled(boolean isEnabled, Pageable pageable);
    Page<User> findAllByRoleAndIsEnabled(String role, boolean isEnabled, Pageable pageable);
}