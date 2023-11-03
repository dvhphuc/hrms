package com.hrms.careerpathmanagement.dto;

import lombok.*;

import java.util.Optional;

@Data
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SkillSetSummarizationDTO {
    Float currentRating;
    Float targetRating;

    public SkillSetSummarizationDTO(Optional<Double> skillSetAvgScore, Optional<Double> skillSetBaselineScore) {
        this.currentRating = skillSetAvgScore.isPresent() ? skillSetAvgScore.get().floatValue() : null;
        this.targetRating = skillSetBaselineScore.isPresent() ? skillSetBaselineScore.get().floatValue() : null;
    }
}
