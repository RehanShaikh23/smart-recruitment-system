package com.AI_Powered.Smart.Recruitment.SmartRecruitment.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LearningResource {
    private String title;
    private String provider;
    private String type;
    private String duration;
    private String difficulty;
    private String url;
}