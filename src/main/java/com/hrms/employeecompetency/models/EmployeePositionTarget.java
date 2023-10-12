package com.hrms.employeecompetency.models;

import com.hrms.employeemanagement.models.Employee;
import com.hrms.employeemanagement.models.PositionLevel;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "employee_position_target")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmployeePositionTarget {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;

    @ManyToOne
    @JoinColumn(name = "position_level_id")
    private PositionLevel positionLevel;
}
