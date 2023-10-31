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
public class PositionSkillSet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "position_skill_set_id")
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "skill_set_id")
    private SkillSet skillSet;
    @ManyToOne
    @JoinColumn(name = "position_id")
    private Position position;
}
