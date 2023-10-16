package com.hrms.employeecompetency.dto;

import com.hrms.employeemanagement.models.PositionLevel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Data
public class CareerPathSummaryDto {
    List<PositionLevel> positionLevels;
}
