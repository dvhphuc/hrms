package com.hrms.employeemanagement.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Collection;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "employeerole")
public class EmployeeRole {
    @Id
    @Column(name = "employeeroleid", nullable = false)
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "employeeid")
    @Column(name = "employeeid")
    private Collection<Employee> employeeId;
    @Column(name = "roleid")
    private String roleId;
}
