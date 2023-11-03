package com.hrms.careerpathmanagement.dto;

import lombok.*;

import java.util.List;

@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RadarChartDTO {
    private List<String> labels;
    private List<RadarDatasetDTO> datasets;
}