package com.AI_Powered.Smart.Recruitment.SmartRecruitment.service;

import com.AI_Powered.Smart.Recruitment.SmartRecruitment.dto.JobRequest;
import com.AI_Powered.Smart.Recruitment.SmartRecruitment.dto.JobResponse;
import com.AI_Powered.Smart.Recruitment.SmartRecruitment.entity.Job;
import com.AI_Powered.Smart.Recruitment.SmartRecruitment.entity.User;
import com.AI_Powered.Smart.Recruitment.SmartRecruitment.exception.ForbiddenException;
import com.AI_Powered.Smart.Recruitment.SmartRecruitment.exception.NotFoundException;
import com.AI_Powered.Smart.Recruitment.SmartRecruitment.repository.JobRepository;
import com.AI_Powered.Smart.Recruitment.SmartRecruitment.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class JobService {


    private final JobRepository repo;
    private final UserRepository userRepo;

    public Job create(JobRequest req, User recruiter) {
        Job j = Job.builder()
                .title(req.getTitle())
                .description(req.getDescription())
                .requiredSkills(String.join(",", req.getRequiredSkills()))
                .recruiter(recruiter)
                .build();
        return repo.save(j);
    }

    public List<JobResponse> list() {
        return repo.findAll().stream()
                .map(j -> JobResponse.builder()
                        .id(j.getId())
                        .title(j.getTitle())
                        .description(j.getDescription())
                        .requiredSkills(List.of(j.getRequiredSkills().split(",")))
                        .recruiterName(j.getRecruiter().getName())
                        .createdDate(j.getCreatedDate())
                        .build())
                .toList();
    }

    public void delete(Long id, User recruiter) {
        Job j = repo.findById(id)
                .orElseThrow(() -> new NotFoundException("Job not found"));
        if (!j.getRecruiter().equals(recruiter)) {
            throw new ForbiddenException("Not your job");
        }
        repo.delete(j);
    }

}
