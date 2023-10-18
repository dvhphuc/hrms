package com.hrms.employeecompetency.models;

import com.hrms.employeemanagement.models.Employee;
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
public class CompetencyEvaluation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "competency_evaluation_id")
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "competency_cycle_id")
    private CompetencyCycle competencyCycle;
    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;
    @ManyToOne
    @JoinColumn(name = "competency_id")
    private Competency competency;
    @ManyToOne
    @JoinColumn(name = "proficiency_level_id")
    private ProficiencyLevel proficiencyLevel;
}
