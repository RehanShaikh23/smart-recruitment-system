package com.AI_Powered.Smart.Recruitment.SmartRecruitment.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JobDescription {
    private Long id;
    private String title;
    private String description;
    private String requiredSkills;
    private String department;
    private String location;
    private String experienceLevel;
}
