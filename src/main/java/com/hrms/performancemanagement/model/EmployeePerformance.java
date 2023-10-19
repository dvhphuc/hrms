package com.hrms.performancemanagement.model;

import com.hrms.employeemanagement.models.Employee;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "employee_performance")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmployeePerformance {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "employee_performance_id")
    private Integer employeePerformanceId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id")
    private Employee employee;

    @Column(name = "self_assessment")
    private Integer selfAssessment;

    @Column(name = "supervisor_assessment")
    private Integer supervisorAssessment;

    @Column(name = "final_assessment")
    private Integer finalAssessment;

    @ManyToOne
    @JoinColumn(name = "performance_cycle_id")
    private PerformanceCycle performanceCycle;

    @Column(name = "potential_score")
    private Integer potentialScore;
}
