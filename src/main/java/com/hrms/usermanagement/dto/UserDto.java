package com.hrms.usermanagement.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.RepresentationModel;

import java.sql.Date;

@Data
public class UserDto {
    private String name;
    private boolean status;
    private String username;

    private Date createdAt;
    private int role;

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

    public void setRole(int role) {
        this.role = role;
    }

    public String getCreatedAt() {
        return createdAt.toString();
    }
}
