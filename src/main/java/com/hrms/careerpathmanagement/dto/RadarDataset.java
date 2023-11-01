package com.hrms.careerpathmanagement.dto;

import lombok.*;

import java.util.List;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RadarDataset {
    private String lineName;
    private List<Float> datasets;
}
