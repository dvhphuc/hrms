package com.hrms.usermanagement.dto;

import com.hrms.usermanagement.model.User;
import org.springframework.hateoas.RepresentationModel;

import java.sql.Date;

public class UserDtoModel extends RepresentationModel<UserDtoModel> {
    private String name;
    private boolean status;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isStatus() {
        return status;
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

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    private String username;

    private Date createdAt;
    private String role;
}
