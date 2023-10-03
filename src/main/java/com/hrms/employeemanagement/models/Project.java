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
@Table(name = "project")
public class Project extends RepresentationModel<Project> {
    @Id
    @Column(name = "projectid", nullable = false)
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @Column(name = "projectname")
    private String projectName;
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "employeeroleid")
    @Column(name = "projectmanagerid")
    private EmployeeRole projectManager;
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "employeeroleid")
    @Column(name = "engagemanagerid")
    private EmployeeRole engagementManager;
}
