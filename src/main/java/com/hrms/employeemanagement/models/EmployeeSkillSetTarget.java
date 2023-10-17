package com.hrms.employeemanagement.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeSkillSetTarget {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer employeeSkillSetTargetId;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    Employee employee;

    @ManyToOne
    @JoinColumn(name = "skill_set_id")
    SkillSet skillSet;

    @Column(name = "target")
    private Integer target;
}
