package com.AI_Powered.Smart.Recruitment.SmartRecruitment.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InterviewRequest {
    private String candidate;
    private String recruiterEmail;
    private String candidateEmail;
    private String startDateTime;
    private String endDateTime;
    private String venue;
}
