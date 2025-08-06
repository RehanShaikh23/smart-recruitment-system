package com.AI_Powered.Smart.Recruitment.SmartRecruitment.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.List;

@Data
public class JobRequest {

    @NotBlank
    private String title;
    private String description;
    private List<String> requiredSkills;

}
