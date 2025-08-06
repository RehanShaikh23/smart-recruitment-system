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
public class CandidateScoreResponse {
    private Long candidateId;
    private String candidateName;
    private int score;
    private String reasoning;
    private List<String> strengths;
    private List<String> weaknesses;
}
