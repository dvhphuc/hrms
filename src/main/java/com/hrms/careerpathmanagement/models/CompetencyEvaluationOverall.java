package com.hrms.careerpathmanagement.models;

import com.hrms.employeemanagement.models.Employee;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CompetencyEvaluationOverall {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "evaluation_overall_id")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id")
    private Employee employee;

    @Column(name = "employee_status")
    private String employeeStatus;

    @Column(name = "evaluator_status")
    private String evaluatorStatus;

    @Column(name = "final_status")
    private String finalStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "competency_cycle_id")
    private CompetencyCycle competencyCycle;

    @Column(name = "score")
    private Float score;

    @Column(name = "last_updated")
    private Date lastUpdated;

    @Column(name = "completed_date")
    private Date completedDate;
}
