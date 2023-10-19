package com.hrms.employeecompetency.models;

import com.hrms.employeemanagement.models.PositionLevel;
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
public class NextPositionLevel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @ManyToOne
    @JoinColumn(name = "current_position_level_id")
    private PositionLevel current;

    @ManyToOne
    @JoinColumn(name = "next_position_level_id")
    private PositionLevel next;
}
