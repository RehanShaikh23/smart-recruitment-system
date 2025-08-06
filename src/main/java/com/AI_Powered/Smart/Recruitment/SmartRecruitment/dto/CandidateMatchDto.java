package com.AI_Powered.Smart.Recruitment.SmartRecruitment.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class CandidateMatchDto {

    private Long candidateId;
    private String name;
    private List<String> matchedSkills;
    private Double matchScore;

}
