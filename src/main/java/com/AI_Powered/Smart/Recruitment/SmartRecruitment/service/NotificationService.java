package com.AI_Powered.Smart.Recruitment.SmartRecruitment.service;

import com.AI_Powered.Smart.Recruitment.SmartRecruitment.entity.Notification;
import com.AI_Powered.Smart.Recruitment.SmartRecruitment.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository repo;
    private final SimpMessagingTemplate messagingTemplate;
    private final EmailService emailService;
    private final SmsService smsService;
    private final SlackService slackService;


    @Async
    public Notification sendInApp(Long userId, String message) {
        Notification notification = Notification.builder()
                .userId(userId)
                .message(message)
                .type("IN_APP")
                .read(false)
                .createdAt(LocalDateTime.now())
                .build();
        repo.save(notification);

        try {
            messagingTemplate.convertAndSend("/topic/notifications/" + userId, notification);
            log.info("✅ In-app notification sent to user {}: {}", userId, message);
        } catch (Exception e) {
            log.error("❌ Failed to send WebSocket notification to user {}: {}", userId, e.getMessage());
        }
        return notification;
    }


    @Async
    public void sendEmailNotification(String email, String subject, String message) {
        emailService.sendEmail(email, subject, message);
        log.info("📧 Email notification sent to {}", email);
    }


    @Async
    public void sendSmsNotification(String phone, String message) {
        smsService.sendSms(phone, message);
        log.info("📱 SMS notification sent to {}", phone);
    }


    @Async
    public void sendSlackNotification(String message) {
        slackService.sendSlackMessage(message);
        log.info("💬 Slack notification sent: {}", message);
    }
}
