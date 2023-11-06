package com.hrms.careerpathmanagement.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;



@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public final class SkillMatrixDataDTO {
    private String name;
    private Double targetSkillLevel;
    private Double skillLevelTotal;
    private Double skillLevelSelf;
    private Double skillLevelManager;
    private Double competencyLevel;
}
