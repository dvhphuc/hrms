package com.hrms.competencymanagement.dto;

public record SkillMatrixData(String name, Float targetSkillLevel, Float skillLevelTotal, Float skillLevelSelf, Float skillLevelManager, Float competencyLevel) {
}
