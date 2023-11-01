package com.hrms.usermanagement.repository;

import com.hrms.usermanagement.model.Role;
import com.hrms.usermanagement.model.UserRole;
import org.springframework.data.jpa.domain.Specification;
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
    List<UserRole> findAll(Specification<UserRole> spec);

    @Query(value = "SELECT role_id FROM user_role WHERE user_id = :userId", nativeQuery = true)
    List<Role> findAllByUserId(Integer userId);

    @Transactional
    @Modifying
    @Query(value = "INSERT INTO user_role (user_id, role_id) VALUES (?1, ?2)", nativeQuery = true)
    void addRoleIdUserId(@Param("userId") Integer userId, @Param("roleId") Integer roleId);

    @Query(value = "SELECT * FROM user_role WHERE user_id IN (:userIds) AND role_id IN (:roleIds)", nativeQuery = true)
    List<UserRole> findByUsersIdAndRolesId(List<Integer> userIds, List<Integer> roleIds);

    @Query(value = "SELECT * FROM user_role WHERE user_id = :userId", nativeQuery = true)
    List<UserRole> findAllByUser(Integer userId);


}
