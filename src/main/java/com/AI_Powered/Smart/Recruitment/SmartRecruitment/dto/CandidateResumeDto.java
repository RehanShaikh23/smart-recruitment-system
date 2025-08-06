package com.AI_Powered.Smart.Recruitment.SmartRecruitment.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CandidateResumeDto {
    private Long candidateId;
    private String name;
    private String email;
    private String skills;
    private String resumeUrl;
    private String downloadUrl;
}