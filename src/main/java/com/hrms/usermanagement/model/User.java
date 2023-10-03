package com.hrms.usermanagement.model;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import lombok.*;

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
    private boolean enabled;

    @JsonProperty("role")
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "roleId")
    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;

    @Column(name = "salt")
    private String salt;
}
