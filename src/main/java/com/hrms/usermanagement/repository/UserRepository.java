package com.hrms.usermanagement.repository;

import com.hrms.employeemanagement.models.User;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {
    User findByUsername(@Param("username") String username);
    Page<User> findAll(Specification<User> spec, Pageable pageable);

    @Modifying
    @Transactional
    @Query(value = "UPDATE User u SET u.isEnabled = :status WHERE u.userId = :userId")
    void updateStatus(@Param("userId") Integer userId, @Param("status") Boolean status);
}