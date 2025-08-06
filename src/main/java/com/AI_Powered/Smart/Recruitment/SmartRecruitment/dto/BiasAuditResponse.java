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
public class BiasAuditResponse {
    private double biasScore;
    private List<String> detectedBiases;
    private List<String> recommendations;
    private boolean isWithinAcceptableRange;
    private String auditSummary;
}