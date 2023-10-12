package com.hrms.usermanagement.dto;

import com.hrms.employeemanagement.models.Role;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.RepresentationModel;

import java.sql.Date;
import java.util.List;
import java.util.Set;

@Data
public class UserDto {

    private Long userId;
    private String name;
    private boolean status;
    private String username;

    private Date createdAt;
    private Set<Role> roles;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean getStatus() {
        return status;
    }

    public List<Role> getRoles() {
        return List.copyOf(roles);
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public String getCreatedAt() {
        return createdAt.toString();
    }
}
