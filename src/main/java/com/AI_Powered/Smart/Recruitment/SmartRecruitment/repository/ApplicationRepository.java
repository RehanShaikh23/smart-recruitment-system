package com.AI_Powered.Smart.Recruitment.SmartRecruitment.repository;

import com.AI_Powered.Smart.Recruitment.SmartRecruitment.entity.Application;
import com.AI_Powered.Smart.Recruitment.SmartRecruitment.entity.Job;
import com.AI_Powered.Smart.Recruitment.SmartRecruitment.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ApplicationRepository extends JpaRepository<Application,Long> {
    List<Application> findByCandidate(User candidate);
    List<Application> findByJob(Job job);
    List<Application> findByJobId(Long jobId);
}
