package com.hrms.careerpathmanagement.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GlanceDTO {
    Float skillGapCurrentScore;
    Float skillGapTargetScore;
    Float competencyLevelPercentage;
}
