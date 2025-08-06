package com.AI_Powered.Smart.Recruitment.SmartRecruitment.controller;

import com.AI_Powered.Smart.Recruitment.SmartRecruitment.dto.JobRequest;
import com.AI_Powered.Smart.Recruitment.SmartRecruitment.dto.JobResponse;
import com.AI_Powered.Smart.Recruitment.SmartRecruitment.entity.Job;
import com.AI_Powered.Smart.Recruitment.SmartRecruitment.entity.User;
import com.AI_Powered.Smart.Recruitment.SmartRecruitment.service.JobService;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/jobs")
public class JobController {

    private final JobService service;

    public JobController(JobService service) {
        this.service = service;
    }

    @GetMapping
    public List<JobResponse> list() { return service.list(); }

    @PostMapping
    @PreAuthorize("hasRole('RECRUITER')")
    public Job create(@Valid @RequestBody JobRequest req, Authentication auth) {
        return service.create(req, (User) auth.getPrincipal());
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('RECRUITER')")
    public void delete(@PathVariable Long id, Authentication auth) {
        service.delete(id, (User) auth.getPrincipal());
    }

}
