package com.hrms.performancemanagement.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class Dataset {
    private String columnsName;
    private List<Float> data;
}
