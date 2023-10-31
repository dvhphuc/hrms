package com.hrms.competencymanagement.models;

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
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "competency_cycle_id")
    private CompetencyCycle competencyCycle;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id")
    private Employee employee;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "competency_id")
    private Competency competency;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "proficiency_level_id")
    private ProficiencyLevel proficiencyLevel;
}
