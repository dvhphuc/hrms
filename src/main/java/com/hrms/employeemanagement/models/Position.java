package com.hrms.employeemanagement.models;

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
public class Position {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "position_id")
    private int id;
    @Column(name = "position_name")
    private String positionName;
    @Column(name = "has_level")
    private Boolean hasLevel;
    @Column(name = "has_department")
    private Boolean hasDepartment;
    public Position(Integer id) {
        this.id = id;
    }
}
