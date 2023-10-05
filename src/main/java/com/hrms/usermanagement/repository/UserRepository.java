package com.hrms.usermanagement.repository;

import com.hrms.usermanagement.model.User;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {
    User findByUsername(@Param("username") String username);

    List<User> findAll(Sort sort);
    Page<User> findAll(Specification<User> spec, Pageable pageable);

    Page<User> findAllByIsEnabled(@Param("status") Boolean isEnable, Pageable pageable);

    Page<User> findAllByRoleNameAndIsEnabled(@Param("roleName") String roleName, @Param("status") Boolean isEnable, Pageable pageable);

}