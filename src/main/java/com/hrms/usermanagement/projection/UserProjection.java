package com.hrms.usermanagement.projection;

import com.hrms.usermanagement.model.Role;
import com.hrms.usermanagement.model.User;
import com.querydsl.core.types.Expression;
import jakarta.persistence.criteria.Root;

import java.util.List;

public interface UserProjection {
    static Expression<User> getProjection(Root<User> root) {
        return (Expression<User>) root;
    }

    Integer getUserId();
    String getUsername();
    Boolean getIsEnabled();
    List<Role> getRoles();
}