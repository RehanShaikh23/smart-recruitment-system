package com.AI_Powered.Smart.Recruitment.SmartRecruitment.controller;

import com.AI_Powered.Smart.Recruitment.SmartRecruitment.dto.*;
import com.AI_Powered.Smart.Recruitment.SmartRecruitment.entity.User;
import com.AI_Powered.Smart.Recruitment.SmartRecruitment.service.AIService;
import com.AI_Powered.Smart.Recruitment.SmartRecruitment.service.ResumeService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.apache.tika.exception.TikaException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/ai")
@RequiredArgsConstructor
public class AIController {

    private final AIService aiService;
    private final ResumeService resumeService;

    @PostMapping("/score-resume")
    @Operation(summary = "Score resume against job description")
    @PreAuthorize("hasAnyAuthority('ROLE_RECRUITER', 'ROLE_ADMIN')")
    public ResponseEntity<ResumeScoreResponse> scoreResume(
            @RequestBody ResumeScoreRequest request,
            Authentication auth) {

        System.out.println("=== CONTROLLER DEBUG ===");
        System.out.println("Request received!");
        System.out.println("Request candidateId: " + request.getCandidateId());
        System.out.println("Request jobDescription: " + request.getJobDescription());

        User user = (User) auth.getPrincipal();
        System.out.println("User role: " + user.getRole());

        if (user.getRole() != User.Role.RECRUITER && user.getRole() != User.Role.ADMIN) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        try {
            System.out.println("Calling resumeService.getResumeText() with candidateId: " + request.getCandidateId());
            String resumeText = resumeService.getResumeText(request.getCandidateId());
            System.out.println("Resume text retrieved: " + (resumeText != null ? "SUCCESS (length: " + resumeText.length() + ")" : "NULL"));

            if (resumeText == null || resumeText.trim().isEmpty()) {
                System.out.println("ERROR: Resume text is null or empty!");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResumeScoreResponse());
            }

            ResumeScoreResponse response = aiService.scoreResumeSync(resumeText, request.getJobDescription());
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            System.out.println("EXCEPTION in resumeService.getResumeText(): " + e.getClass().getSimpleName() + " - " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResumeScoreResponse());
        }
    }

    @PostMapping("/skill-gap-analysis")
    @Operation(summary = "Analyze skill gaps and suggest learning resources")
    public ResponseEntity<SkillGapResponse> analyzeSkillGaps(
            @RequestBody SkillGapRequest request,
            Authentication auth) {

        User user = (User) auth.getPrincipal();

        if (user.getRole() == User.Role.CANDIDATE && !request.getCandidateId().equals(user.getId())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        } else if (user.getRole() != User.Role.RECRUITER && user.getRole() != User.Role.ADMIN) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        try {
            String candidateSkills = resumeService.getCandidateSkills(request.getCandidateId());
            SkillGapResponse response = aiService.analyzeSkillGapsSync(candidateSkills,
                    Arrays.asList(request.getRequiredSkills().split(",")));
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/bias-audit")
    @Operation(summary = "Audit candidate selection for bias")
    public ResponseEntity<BiasAuditResponse> auditBias(
            @RequestBody BiasAuditRequest request,
            Authentication auth) {

        User user = (User) auth.getPrincipal();

        if (user.getRole() != User.Role.ADMIN) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        try {
            List<String> candidateNames = resumeService.getCandidateNames(request.getCandidateIds());
            BiasAuditResponse response = aiService.auditBiasSync(candidateNames, request.getJobTitle());
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/batch-score/{jobId}")
    @Operation(summary = "Score all candidates for a specific job")
    public ResponseEntity<List<CandidateScoreResponse>> batchScore(
            @PathVariable Long jobId,
            Authentication auth) {

        User user = (User) auth.getPrincipal();

        if (user.getRole() != User.Role.RECRUITER && user.getRole() != User.Role.ADMIN) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        return ResponseEntity.ok(List.of());
    }
}
