package com.AI_Powered.Smart.Recruitment.SmartRecruitment.controller;

import com.AI_Powered.Smart.Recruitment.SmartRecruitment.dto.SkillGapDto;
import com.AI_Powered.Smart.Recruitment.SmartRecruitment.entity.User;
import com.AI_Powered.Smart.Recruitment.SmartRecruitment.service.AnalyticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/analytics")
@RequiredArgsConstructor
public class AnalyticsController {

    private final AnalyticsService service;

    @GetMapping("/job/{jobId}")
    @PreAuthorize("hasRole('RECRUITER')")
    public Map<String, Long> jobStats(@PathVariable Long jobId, Authentication auth) {
        return service.jobStats(jobId, (User) auth.getPrincipal());
    }

    @GetMapping("/skill-gap")
    @PreAuthorize("hasRole('CANDIDATE')")
    public List<SkillGapDto> skillGap(Authentication auth) {
        return service.skillGap(((User) auth.getPrincipal()).getId());
    }


}
