package com.hrms.employeecompetency.dto;

import com.hrms.employeecompetency.models.PerformanceRange;
import com.hrms.employeemanagement.models.JobLevel;
import lombok.*;

import java.util.HashMap;
import java.util.List;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PerformanceByJobLevelChart { //DTO
    private HashMap< PerformanceRange, PerformanceRangeData > chart; // Integer: categoryId (ex: "Outstanding", List<Float>: data)
    public class PerformanceRangeData {
        List<JobLevel> jobLevel;
        List<Float> data;
    }
}
