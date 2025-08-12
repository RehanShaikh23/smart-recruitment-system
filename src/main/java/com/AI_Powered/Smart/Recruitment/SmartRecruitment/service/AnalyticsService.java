package com.AI_Powered.Smart.Recruitment.SmartRecruitment.service;

import com.AI_Powered.Smart.Recruitment.SmartRecruitment.dto.SkillGapDto;
import com.AI_Powered.Smart.Recruitment.SmartRecruitment.entity.Application;
import com.AI_Powered.Smart.Recruitment.SmartRecruitment.entity.CandidateProfile;
import com.AI_Powered.Smart.Recruitment.SmartRecruitment.entity.Job;
import com.AI_Powered.Smart.Recruitment.SmartRecruitment.entity.User;
import com.AI_Powered.Smart.Recruitment.SmartRecruitment.exception.ForbiddenException;
import com.AI_Powered.Smart.Recruitment.SmartRecruitment.repository.ApplicationRepository;
import com.AI_Powered.Smart.Recruitment.SmartRecruitment.repository.CandidateProfileRepository;
import com.AI_Powered.Smart.Recruitment.SmartRecruitment.repository.JobRepository;
import lombok.RequiredArgsConstructor;
import com.AI_Powered.Smart.Recruitment.SmartRecruitment.exception.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AnalyticsService {

    private final ApplicationRepository appRepo;
    private final JobRepository jobRepo;
    private final CandidateProfileRepository cpRepo;

    public Map<String, Long> jobStats(Long jobId, User recruiter) throws NotFoundException, ForbiddenException {
        Job j = jobRepo.findById(jobId).orElseThrow(() -> new NotFoundException("Job not found"));
        if (!j.getRecruiter().equals(recruiter)) throw new ForbiddenException("Not your job");
        List<Application> apps = appRepo.findByJob(j);
        return Map.of(
                "applied", apps.stream().filter(a -> a.getStatus() == Application.Status.APPLIED).count(),
                "shortlisted", apps.stream().filter(a -> a.getStatus() == Application.Status.SHORTLISTED).count(),
                "rejected", apps.stream().filter(a -> a.getStatus() == Application.Status.REJECTED).count()
        );
    }

    public List<SkillGapDto> skillGap(Long userId) {
        CandidateProfile cp = cpRepo.findByUserId(userId)
                .orElseThrow(() -> new NotFoundException("Candidate profile not found for userId: " + userId));


        Set<String> candidateSkills = (cp.getSkills() == null || cp.getSkills().isBlank())
                ? Set.of()
                : Arrays.stream(cp.getSkills().split(","))
                .map(String::trim)
                .filter(s -> !s.isBlank())
                .collect(Collectors.toSet());

        return jobRepo.findAll().stream()
                .flatMap(j -> Arrays.stream(j.getRequiredSkills().split(",")))
                .map(String::trim)
                .filter(s -> !s.isBlank())
                .filter(s -> !candidateSkills.contains(s))
                .distinct()
                .map(SkillGapDto::new)
                .toList();
    }


}
