package com.hrms.careerpathmanagement.dto;

import lombok.*;

@Data
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SkillSetSummarization {
    Float currentRating;
    Float targetRating;

    public SkillSetSummarization(Double skillSetAvgScore, Double skillSetBaselineScore) {
        this.currentRating = skillSetAvgScore.floatValue();
        this.targetRating = skillSetBaselineScore.floatValue();
    }
}
