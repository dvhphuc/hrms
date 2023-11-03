package com.hrms.careerpathmanagement.models;


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
@AllArgsConstructor
@NoArgsConstructor
public class SkillSetEvaluation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "skill_set_evaluation_id")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "competency_cycle_id")
    private CompetencyCycle competencyCycle;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id")
    private Employee employee;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "skill_set_id")
    private SkillSet skillSet;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "employee_proficiency_level_id", referencedColumnName = "proficiency_level_id")
    private ProficiencyLevel employeeProficiencyLevel;

    @Column(name = "employee_comment")
    private String employeeComment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "evaluator_proficiency_level_id", referencedColumnName = "proficiency_level_id")
    private ProficiencyLevel evaluatorProficiencyLevel;

    @Column(name = "evaluator_comment")
    private String evaluatorComment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "final_proficiency_level_id", referencedColumnName = "proficiency_level_id")
    private ProficiencyLevel finalProficiencyLevel;

    @Column(name = "final_comment")
    private String finalComment;
}
