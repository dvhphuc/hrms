package com.hrms.careerpathmanagement.dto;

import lombok.Data;

import java.util.List;

@Data
public class CareerPathSummaryDto {
    List<PositionMatchDTO> positionLevels;
}
