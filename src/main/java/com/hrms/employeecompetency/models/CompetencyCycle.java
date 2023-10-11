package com.hrms.employeecompetency.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class CompetencyCycle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "competency_cycle_id")
    private int id;

    @Column(name = "competency_cycle_name")
    private String competencyCycleName;
    @Column(name = "start_date")
    private String startDate;
    @Column(name = "due_date")
    private String dueDate;
    @Column(name = "year")
    private String year;
}
