package com.hrms.employeecompetency.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProficiencyLevel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "proficiency_level_id")
    private int ID;
    @Column(name = "proficiency_level_name")
    private String ProficiencyLevelName;
    @Column(name = "weight")
    private int Weight;
}

