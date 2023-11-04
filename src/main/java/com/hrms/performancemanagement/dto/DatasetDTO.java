package com.hrms.performancemanagement.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class DatasetDTO {
    private String columnName;
    private List<Float> data;
}
