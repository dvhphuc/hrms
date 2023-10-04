package com.hrms.employeemanagement.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

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
    @JsonIgnore
    private List<EmployeeRole> employeeCompanyRoles;
}
