package com.hrms.careerpathmanagement.models;

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
public class CompetencyTimeLine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "competency_time_line_id")
    private Integer id;

    @Column(name = "competency_time_line_name")
    private String competencyTimeLineName;

    @Column(name = "start_date")
    private Date startDate;

    @Column(name = "due_date")
    private Date dueDate;

    @ManyToOne
    @JoinColumn(name = "competency_cycle_id")
    private CompetencyCycle competencyCycle;
    
    @Column(name = "is_done")
    private Boolean isDone;

}
