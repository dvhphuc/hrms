package com.hrms.employeecompetency.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RadarChart {
    private List<String> labels;
    private List<RadarDataset> datasets;
}
