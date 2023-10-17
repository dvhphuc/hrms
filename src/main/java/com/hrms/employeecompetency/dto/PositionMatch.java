package com.hrms.employeecompetency.dto;

import com.hrms.employeemanagement.models.PositionLevel;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class PositionMatch {
    PositionLevel positionLevel;
    Float matchPercentage;
}
