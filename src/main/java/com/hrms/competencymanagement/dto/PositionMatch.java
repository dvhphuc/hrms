package com.hrms.competencymanagement.dto;

import com.hrms.employeemanagement.models.PositionLevel;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PositionMatch {
    PositionLevel positionLevel;
    Float matchPercentage;
}
