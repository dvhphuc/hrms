package com.hrms.competencymanagement.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RadarValue {
    private Integer competencyCycleId;
    private Integer competencyId;
    private Float average;
}
