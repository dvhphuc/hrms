package com.hrms.employeecompetency.input;

import com.hrms.employeecompetency.models.Competency;
import com.hrms.employeemanagement.models.JobLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AvgCompetency {
    private JobLevel jobLevel;
    private Competency competency;
    private Float average;
}
