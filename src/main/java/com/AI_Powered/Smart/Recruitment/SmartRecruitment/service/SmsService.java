package com.AI_Powered.Smart.Recruitment.SmartRecruitment.service;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class SmsService {

    @Value("${twilio.account.sid}")
    private String accountSid;

    @Value("${twilio.auth.token}")
    private String authToken;

    @Value("${twilio.phone.number}")
    private String fromNumber;

    @PostConstruct
    public void initTwilio() {
        Twilio.init(accountSid, authToken);
        log.info("✅ Twilio initialized successfully");
    }

    @Async
    public void sendSms(String to, String message) {
        try {
            Message.creator(new PhoneNumber(to), new PhoneNumber(fromNumber), message).create();
            log.info("📩 SMS sent successfully to {}", to);
        } catch (Exception e) {
            log.error("❌ Failed to send SMS to {} - {}", to, e.getMessage());
        }
    }
}
