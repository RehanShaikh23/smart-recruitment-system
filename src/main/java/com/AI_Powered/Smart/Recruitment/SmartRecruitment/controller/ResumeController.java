package com.AI_Powered.Smart.Recruitment.SmartRecruitment.controller;

import com.AI_Powered.Smart.Recruitment.SmartRecruitment.dto.CandidateResumeDto;
import com.AI_Powered.Smart.Recruitment.SmartRecruitment.entity.CandidateProfile;
import com.AI_Powered.Smart.Recruitment.SmartRecruitment.entity.User;
import com.AI_Powered.Smart.Recruitment.SmartRecruitment.service.ResumeService;
import io.swagger.v3.oas.annotations.Operation;
import org.apache.tika.exception.TikaException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/resume")
public class ResumeController {

    private final ResumeService service;

    public ResumeController(ResumeService service) {
        this.service = service;
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Upload resume")
    public CandidateProfile upload(@RequestParam("file") MultipartFile file,
                                   Authentication auth) throws IOException, TikaException {
        User user = (User) auth.getPrincipal();
        return service.uploadResume(file, user);
    }

    @GetMapping("/{candidateId}")
    @Operation(summary = "View candidate resume")
    public ResponseEntity<byte[]> viewResume(@PathVariable Long candidateId,
                                             Authentication auth) throws IOException {
        User user = (User) auth.getPrincipal();


        if (user.getRole() != User.Role.RECRUITER && user.getRole() != User.Role.ADMIN) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        byte[] resumeData = service.getResumeByCandidate(candidateId);
        CandidateProfile candidate = service.getCandidateProfile(candidateId);

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_PDF)
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" +
                        candidate.getUser().getName() + "_resume.pdf\"")
                .body(resumeData);
    }

    @GetMapping("/{candidateId}/download")
    @Operation(summary = "Download candidate resume")
    public ResponseEntity<byte[]> downloadResume(@PathVariable Long candidateId,
                                                 Authentication auth) throws IOException {
        User user = (User) auth.getPrincipal();

        if (user.getRole() != User.Role.RECRUITER && user.getRole() != User.Role.ADMIN) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        byte[] resumeData = service.getResumeByCandidate(candidateId);
        CandidateProfile candidate = service.getCandidateProfile(candidateId);

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_PDF)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" +
                        candidate.getUser().getName() + "_resume.pdf\"")
                .body(resumeData);
    }

    @GetMapping("/candidates")
    @Operation(summary = "Get all candidates for recruiters")
    public ResponseEntity<List<CandidateResumeDto>> getAllCandidates(Authentication auth) {
        User user = (User) auth.getPrincipal();

        if (user.getRole() != User.Role.RECRUITER && user.getRole() != User.Role.ADMIN) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        List<CandidateProfile> candidates = service.getAllCandidates();

        List<CandidateResumeDto> dtos = candidates.stream()
                .filter(candidate -> candidate.getResumeUrl() != null)
                .map(candidate -> CandidateResumeDto.builder()
                        .candidateId(candidate.getId())
                        .name(candidate.getUser().getName())
                        .email(candidate.getUser().getEmail())
                        .skills(candidate.getSkills())
                        .resumeUrl("/api/resume/" + candidate.getId())
                        .downloadUrl("/api/resume/" + candidate.getId() + "/download")
                        .build())
                .toList();

        return ResponseEntity.ok(dtos);
    }
}