package com.AI_Powered.Smart.Recruitment.SmartRecruitment.repository;

import com.AI_Powered.Smart.Recruitment.SmartRecruitment.entity.CandidateProfile;
import com.AI_Powered.Smart.Recruitment.SmartRecruitment.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CandidateProfileRepository extends JpaRepository<CandidateProfile,Long> {
    Optional<CandidateProfile> findByUser(User user);
}
