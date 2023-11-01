package com.hrms.usermanagement.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    private Integer roleId;
    @Column(name = "name", length = 32)
    private String name;

    @Override
    public String toString() {
        return "Role{" +
                " Role ='" + name + '\'' +
                '}';
    }

    static String USER_ROLE = "USER";
    static String ADMIN_ROLE = "ADMIN";
    static String MANAGER_ROLE = "MANAGER";
}
