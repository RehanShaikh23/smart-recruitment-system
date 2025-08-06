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
public class ResumeScoreResponse {
    private int overallScore;
    private String feedback;
    private List<String> strengths;
    private List<String> areasForImprovement;
    private List<String> matchedSkills;
    private List<String> missingSkills;
}