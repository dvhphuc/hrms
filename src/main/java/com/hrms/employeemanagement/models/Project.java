package com.hrms.employeemanagement.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Project extends RepresentationModel<Project> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "project_id")
    private int id;
    @Column(name = "project_name")
    private String projectName;

    @ManyToOne
    @JoinColumn(name = "project_mamnager_id")
    private Employee projectManager;

    @ManyToOne
    @JoinColumn(name = "engage_manager_id")
    private Employee engageManager;

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL)
    private List<EmployeeProject> employeeProjects;
}
