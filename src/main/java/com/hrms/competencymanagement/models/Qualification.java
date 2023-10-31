package com.hrms.employeecompetency.models;

import com.hrms.employeemanagement.models.Employee;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Qualification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer qualificationId;
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    private Employee employee;
    private String location;
    private Date uploadedDate;
    private String issuedBy;
}
