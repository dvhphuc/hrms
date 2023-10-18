package com.hrms.employeecompetency.models;


import com.hrms.employeemanagement.models.Employee;
import com.hrms.employeemanagement.models.PositionSkillSet;
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
    @ManyToOne
    @JoinColumn(name = "competency_cycle_id")
    private CompetencyCycle competencyCycle;
    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;
    @ManyToOne
    @JoinColumn(name = "position_skill_set_id")
    private PositionSkillSet positionSkillSet;
    @ManyToOne
    @JoinColumn(name = "employee_proficiency_level_id", referencedColumnName = "proficiency_level_id")
    private ProficiencyLevel employeeProficiencyLevel;
    private String employeeComment;
    @ManyToOne
    @JoinColumn(name = "evaluator_proficiency_level_id", referencedColumnName = "proficiency_level_id")
    private ProficiencyLevel evaluatorProficiencyLevel;
    private String evaluatorComment;
    @ManyToOne
    @JoinColumn(name = "final_proficiency_level_id", referencedColumnName = "proficiency_level_id")
    private ProficiencyLevel finalProficiencyLevel;
    private String finalComment;
}
