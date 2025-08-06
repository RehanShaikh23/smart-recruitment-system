package com.AI_Powered.Smart.Recruitment.SmartRecruitment.controller;

import com.AI_Powered.Smart.Recruitment.SmartRecruitment.dto.CandidateMatchDto;
import com.AI_Powered.Smart.Recruitment.SmartRecruitment.service.SkillMatchingService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/match")
public class SkillMatchingController {

    private final SkillMatchingService service;

    public SkillMatchingController(SkillMatchingService service) {
        this.service = service;
    }

    @GetMapping("/{jobId}")
    @PreAuthorize("hasRole('RECRUITER')")
    public List<CandidateMatchDto> match(@PathVariable Long jobId) {
        return service.match(jobId);
    }

}
