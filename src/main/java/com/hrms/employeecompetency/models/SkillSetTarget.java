package com.hrms.employeecompetency.models;

import com.hrms.employeemanagement.models.Employee;
import com.hrms.employeemanagement.models.SkillSet;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SkillSetTarget {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "competency_cycle_id")
    CompetencyCycle competencyCycle;
    @ManyToOne
    @JoinColumn(name = "employee_id")
    Employee employee;
    @ManyToOne
    @JoinColumn(name = "skill_set_id")
    SkillSet skillSet;
    @ManyToOne
    @JoinColumn(name = "target_proficiency_level_id", referencedColumnName = "proficiency_level_id")
    private ProficiencyLevel targetProficiencyLevel;
}
