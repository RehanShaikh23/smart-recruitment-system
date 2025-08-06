package com.AI_Powered.Smart.Recruitment.SmartRecruitment.service;

import com.AI_Powered.Smart.Recruitment.SmartRecruitment.dto.BiasAuditResponse;
import com.AI_Powered.Smart.Recruitment.SmartRecruitment.dto.ResumeScoreResponse;
import com.AI_Powered.Smart.Recruitment.SmartRecruitment.dto.SkillGapResponse;
import reactor.core.publisher.Mono;
import java.util.List;

public interface AIService {

    Mono<ResumeScoreResponse> scoreResume(String resumeText, String jobDescription);

    Mono<SkillGapResponse> analyzeSkillGaps(String candidateSkills, List<String> requiredSkills);

    Mono<BiasAuditResponse> auditBias(List<String> candidateNames, String jobTitle);


    default ResumeScoreResponse scoreResumeSync(String resumeText, String jobDescription) {
        return scoreResume(resumeText, jobDescription).block();
    }

    default SkillGapResponse analyzeSkillGapsSync(String candidateSkills, List<String> requiredSkills) {
        return analyzeSkillGaps(candidateSkills, requiredSkills).block();
    }

    default BiasAuditResponse auditBiasSync(List<String> candidateNames, String jobTitle) {
        return auditBias(candidateNames, jobTitle).block();
    }
}
