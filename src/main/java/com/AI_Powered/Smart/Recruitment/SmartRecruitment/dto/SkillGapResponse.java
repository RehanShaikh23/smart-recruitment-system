package com.AI_Powered.Smart.Recruitment.SmartRecruitment.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SkillGapResponse {
    private List<String> skillGaps;
    private List<LearningResource> learningResources;
    private String priorityLevel;
    private String estimatedLearningTime;
}