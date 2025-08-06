package com.AI_Powered.Smart.Recruitment.SmartRecruitment.controller;

import com.AI_Powered.Smart.Recruitment.SmartRecruitment.entity.Application;
import com.AI_Powered.Smart.Recruitment.SmartRecruitment.entity.User;
import com.AI_Powered.Smart.Recruitment.SmartRecruitment.service.ApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ApplicationController {

    private final ApplicationService service;

    @PostMapping("/applications/{jobId}")
    @PreAuthorize("hasRole('CANDIDATE')")
    public Application apply(@PathVariable Long jobId, Authentication auth) {
        return service.apply(jobId, (User) auth.getPrincipal());
    }

    @PatchMapping("/applications/{id}/status")
    @PreAuthorize("hasRole('RECRUITER')")
    public Application updateStatus(@PathVariable Long id,
                                    @RequestParam Application.Status status,
                                    Authentication auth) {
        return service.updateStatus(id, status, (User) auth.getPrincipal());
    }

    @GetMapping("/applications/job/{jobId}/applicants")
    @PreAuthorize("hasRole('RECRUITER')")
    public List<User> getApplicants(@PathVariable Long jobId) {
        return service.getApplicantsByJobId(jobId);
    }

}
