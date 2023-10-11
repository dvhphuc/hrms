package com.hrms.employeecompetency.models;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

public class CompetencyTimeLine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "competency_cycle_id")
    private int id;

    @Column(name = "competency_time_line_name")
    private String competencyTimeLineName;
    @Column(name = "start_date")
    private String startDate;
    @Column(name = "due_date")
    private String dueDate;

}
