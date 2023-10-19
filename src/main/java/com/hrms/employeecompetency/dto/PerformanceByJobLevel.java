package com.hrms.employeecompetency.dto;

import com.hrms.employeemanagement.models.JobLevel;
import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PerformanceByJobLevel {
    JobLevel jobLevel;
    float early;
    float unsatis;
    float pme;
    float meetExpectation;
    float exceedExpectation;
    float outstanding;
}
