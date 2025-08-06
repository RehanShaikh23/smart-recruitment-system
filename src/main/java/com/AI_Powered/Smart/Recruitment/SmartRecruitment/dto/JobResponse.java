package com.AI_Powered.Smart.Recruitment.SmartRecruitment.dto;

import lombok.Builder;
import lombok.Data;

import java.time.Instant;
import java.util.List;

@Data
@Builder
public class JobResponse {

    private Long id;
    private String title;
    private String description;
    private List<String> requiredSkills;
    private String recruiterName;
    private Instant createdDate = Instant.now();

}
