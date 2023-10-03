package com.hrms.employeemanagement.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "employeeproject")
public class EmployeeProject extends RepresentationModel<EmployeeProject> {
    @Id
    @Column(name = "employeeprojectid", nullable = false)
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @Column(name = "projectid")
    private String teamProjectId;
    @Column(name = "employeeid")
    private String employeeId;
}
