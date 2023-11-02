package com.hrms.performancemanagement.dto;


import com.hrms.employeemanagement.models.JobLevel;
import lombok.*;

import java.util.List;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PerformanceByJobLevelChart { //DTO
    private List<String> labels;
    private List<Dataset> datasets;
}
