package com.hrms.employeemanagement.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CompanyRole {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "company_role_id")
    private int id;
    @Column(name = "company_role_name")
    private String CompanyRoleName;

    @OneToMany(mappedBy = "companyRole", cascade = CascadeType.ALL)
    private List<EmployeeRole> employeeCompanyRoles;
}
