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
    private Float selfAssessment;

    @Column(name = "supervisor_assessment")
    private Float supervisorAssessment;

    @Column(name = "final_assessment")
    private Float finalAssessment;

    @Column(name = "performance_cycle_id")
    @JoinTable(name = "performance_cycle", joinColumns = @JoinColumn(name = "performance_cycle_id"))
    private Integer performanceCycleId;
}
