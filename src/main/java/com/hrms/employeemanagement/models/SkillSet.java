package com.hrms.employeemanagement.models;

import com.hrms.employeecompetency.models.Competency;
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
public class SkillSet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "skill_set_id")
    private int ID;
    @Column(name = "skill_set_name")
    private String skillSetName;

    @ManyToOne
    @JoinColumn(name = "competency_id")
    private Competency competency;

}