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
    private int id;
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
    @JoinColumn(name = "employee_proficiency_level_id", referencedColumnName = "proficiency_level_id")
    private ProficiencyLevel employeeProficiencyLevel;
    @Column(name = "employee_comment")
    private String employeeComment;
    @ManyToOne
    @JoinColumn(name = "evaluator_proficiency_level_id", referencedColumnName = "proficiency_level_id")
    private ProficiencyLevel evaluatorProficiencyLevel;
    @Column(name = "evaluator_comment")
    private String evaluatorComment;
    @Column(name = "final_score")
    private int finalScore;
    @Column(name = "final_comment")
    private String finalComment;
}
