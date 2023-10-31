package com.hrms.competencymanagement.dto;

import lombok.Data;

import java.util.List;

@Data
public class CareerPathSummaryDto {
    List<PositionMatch> positionLevels;
}
