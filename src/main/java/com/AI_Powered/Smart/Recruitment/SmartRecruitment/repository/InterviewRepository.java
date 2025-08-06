package com.AI_Powered.Smart.Recruitment.SmartRecruitment.repository;

import com.AI_Powered.Smart.Recruitment.SmartRecruitment.entity.Interview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InterviewRepository extends JpaRepository<Interview, Long> {
}
