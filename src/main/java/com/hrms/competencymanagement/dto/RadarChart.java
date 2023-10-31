package com.hrms.competencymanagement.dto;

import lombok.*;

import java.util.List;

@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RadarChart {
    private List<String> labels;
    private List<RadarDataset> datasets;
}