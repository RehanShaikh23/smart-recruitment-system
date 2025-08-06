package com.AI_Powered.Smart.Recruitment.SmartRecruitment.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class SlackService {

    private final RestTemplate restTemplate;

    @Value("${slack.webhook.url}")
    private String webhookUrl;

    public SlackService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Async
    public void sendSlackMessage(String text) {
        try {
            Map<String, String> payload = new HashMap<>();
            payload.put("text", text);

            restTemplate.postForObject(webhookUrl, payload, String.class);
            log.info("✅ Slack message sent: {}", text);
        } catch (Exception e) {
            log.error("❌ Failed to send Slack message: {}", e.getMessage());
        }
    }
}
