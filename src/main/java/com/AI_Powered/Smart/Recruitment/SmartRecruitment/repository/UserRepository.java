package com.AI_Powered.Smart.Recruitment.SmartRecruitment.repository;

import com.AI_Powered.Smart.Recruitment.SmartRecruitment.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findByEmail(String email);
}
