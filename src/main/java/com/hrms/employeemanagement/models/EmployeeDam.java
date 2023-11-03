package com.hrms.employeemanagement.models;

import com.hrms.damservice.models.SourceFile;
import com.hrms.damservice.models.SourceType;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmployeeDam {
    @Id
    @Column(name = "employee_source_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id")
    private Employee employee;

    @Column(name = "employee_id")
    private String publicId;

    @Column(name = "employee_name")
    private String type;
}

