package com.AI_Powered.Smart.Recruitment.SmartRecruitment.service;

import com.AI_Powered.Smart.Recruitment.SmartRecruitment.dto.CandidateMatchDto;
import com.AI_Powered.Smart.Recruitment.SmartRecruitment.entity.Job;
import com.AI_Powered.Smart.Recruitment.SmartRecruitment.exception.NotFoundException;
import com.AI_Powered.Smart.Recruitment.SmartRecruitment.repository.CandidateProfileRepository;
import com.AI_Powered.Smart.Recruitment.SmartRecruitment.repository.JobRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class SkillMatchingService {

    private final JobRepository jobRepo;
    private final CandidateProfileRepository cpRepo;

    public List<CandidateMatchDto> match(Long jobId) {
        Job job = jobRepo.findById(jobId).orElseThrow(() -> new NotFoundException("Job not found"));
        Set<String> jobSkills = Set.of(job.getRequiredSkills().split(","));

        return cpRepo.findAll().stream()
                .map(c -> {
                    Set<String> candidateSkills = Set.of(c.getSkills().split(","));
                    Set<String> matched = new HashSet<>(jobSkills);
                    matched.retainAll(candidateSkills);

                    double score = (double) matched.size() / jobSkills.size();
                    return CandidateMatchDto.builder()
                            .candidateId(c.getUser().getId())
                            .name(c.getUser().getName())
                            .matchedSkills(new ArrayList<>(matched))
                            .matchScore(score)
                            .build();
                })
                .sorted(Comparator.comparing(CandidateMatchDto::getMatchScore).reversed())
                .toList();
    }



}
