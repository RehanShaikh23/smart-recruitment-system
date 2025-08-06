package com.AI_Powered.Smart.Recruitment.SmartRecruitment.repository;

import com.AI_Powered.Smart.Recruitment.SmartRecruitment.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification,Long> {
    List<Notification> findByUserIdAndReadFalse(Long userId);
}
