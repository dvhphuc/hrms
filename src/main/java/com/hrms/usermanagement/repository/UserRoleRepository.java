package com.hrms.usermanagement.repository;

import com.hrms.employeemanagement.models.Role;
import com.hrms.employeemanagement.models.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, Integer>, JpaSpecificationExecutor<UserRole> {

    @Transactional
    @Modifying
    @Query(value = "INSERT INTO user_role (user_id, role_id) VALUES (?1, ?2)", nativeQuery = true)
    void addRoleIdUserId(@Param("userId") Integer userId, @Param("roleId") Integer roleId);

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM user_role WHERE user_id = ?1 AND role_id NOT IN ?2", nativeQuery = true)
    void deleteAllByUserIdNotInRoles(@Param("userId") Integer userId, @Param("roles") List<Integer> roleIds);

    List<UserRole> findAllByUserUserId(Integer userId);
}
