package com.hrms.employeemanagement.models;

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
public class Skill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "skill_id")
    private int ID;
    @Column(name = "skill_name")
    private String skillName;

    @ManyToOne
    @JoinColumn(name = "skill_set_id")
    private SkillSet skillset;

    // getters and setters
}