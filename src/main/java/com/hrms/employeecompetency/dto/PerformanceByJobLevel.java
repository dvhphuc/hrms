package com.hrms.employeecompetency.dto;

import com.hrms.employeemanagement.models.JobLevel;
import kotlinx.coroutines.Job;
import lombok.*;

import java.util.List;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PerformanceByJobLevel {
    private List<JobLevel> labels;
    private List<String> categories;
    private List<List<Float>> datasets;
}
