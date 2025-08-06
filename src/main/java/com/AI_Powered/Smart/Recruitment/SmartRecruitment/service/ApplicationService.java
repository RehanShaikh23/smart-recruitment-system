package com.AI_Powered.Smart.Recruitment.SmartRecruitment.service;

import com.AI_Powered.Smart.Recruitment.SmartRecruitment.entity.Application;
import com.AI_Powered.Smart.Recruitment.SmartRecruitment.entity.Job;
import com.AI_Powered.Smart.Recruitment.SmartRecruitment.entity.User;
import com.AI_Powered.Smart.Recruitment.SmartRecruitment.repository.ApplicationRepository;
import com.AI_Powered.Smart.Recruitment.SmartRecruitment.repository.JobRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ApplicationService {

    private final ApplicationRepository applicationRepository;
    private final JobRepository jobRepository;

    public ApplicationService(ApplicationRepository applicationRepository, JobRepository jobRepository) {
        this.applicationRepository = applicationRepository;
        this.jobRepository = jobRepository;
    }

    public Application apply(Long jobId, User candidate) {
        Job job = jobRepository.findById(jobId)
                .orElseThrow(() -> new RuntimeException("Job not found with ID: " + jobId));

        Application application = new Application();
        application.setJob(job);
        application.setCandidate(candidate);
        application.setStatus(Application.Status.APPLIED);

        return applicationRepository.save(application);
    }

    public Application updateStatus(Long id, Application.Status status, User recruiter) {
        Application application = applicationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Application not found with ID: " + id));

        application.setStatus(status);
        return applicationRepository.save(application);
    }

    public List<User> getApplicantsByJobId(Long jobId) {
        return applicationRepository.findByJobId(jobId)
                .stream()
                .map(Application::getCandidate)
                .collect(Collectors.toList());
    }

}
