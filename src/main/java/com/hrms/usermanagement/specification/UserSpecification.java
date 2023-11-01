package com.hrms.usermanagement.specification;

import com.fasterxml.jackson.annotation.JacksonAnnotationsInside;
import com.hrms.usermanagement.model.User;
import com.hrms.usermanagement.model.UserRole;
import jakarta.annotation.Nullable;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import java.util.List;

@Component
public class UserSpecification {
    public Specification<User> hasId(@Nullable Integer id) {
        return id == null ? null : (root, query, cb) -> cb.equal(root.get("id"), id);
    }

    public Specification<User> hasRoles(@Nullable List<Integer> roleIds) {
        return roleIds == null ? null : (root, query, cb) -> {
            Join<User, UserRole> userRoleJoin = root.join("userRoles", JoinType.LEFT);
            return userRoleJoin.get("id").in(roleIds);
        };
    }

    public Specification<User> hasStatus(@Nullable Boolean status) {
        return  status == null ? null : (root, query, cb) -> cb.equal(root.get("status"), status);
    }

    public Specification<User> hasSearch(@Nullable String search) {
        return search == null ? null : (root, query, cb) -> cb.like(root.get("username"), "%" + search + "%");
    }

    public Specification<User> getUsersSpec(@Nullable String search,
                                         List<Integer> roleIds,
                                         @Nullable Boolean status)
    {
        return Specification.where(hasSearch(search))
                .and(hasRoles(roleIds))
                .and(hasStatus(status));
    }
}
