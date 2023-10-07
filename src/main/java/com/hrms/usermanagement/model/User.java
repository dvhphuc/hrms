package com.hrms.usermanagement.model;

import com.fasterxml.jackson.annotation.*;
import com.hrms.employeemanagement.models.Employee;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Date;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString(of = {"username", "enabled", "role"})
@Table(name = "Users")
public class User {
    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(name = "username", unique = true)
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "enabled")
    private Boolean isEnabled;

    @JsonProperty("role")
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "roleId")
    @ManyToOne
    @JoinColumn(name = "role_id")
    @Nullable
    private Role role;

    @Column(name = "created_at")
    private Date createdAt;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "employee_id", referencedColumnName = "employee_id")
    private Employee employee;

}
