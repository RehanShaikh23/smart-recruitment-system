package com.AI_Powered.Smart.Recruitment.SmartRecruitment.controller;

import com.AI_Powered.Smart.Recruitment.SmartRecruitment.service.EmailService;
import com.AI_Powered.Smart.Recruitment.SmartRecruitment.service.SlackService;
import com.AI_Powered.Smart.Recruitment.SmartRecruitment.service.SmsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/test")
@RequiredArgsConstructor
public class TestController {

    private final EmailService emailService;
    private final SlackService slackService;
    private final SmsService smsService;

    @GetMapping("/email")
    public String testEmail(@RequestParam String to) {
        emailService.sendEmail(to, "✅ Test Email", "This is a test email from SmartRecruitment.");
        return "Email sent to " + to;
    }
    @PostMapping("/email")
    public String sendEmail(
            @RequestParam String to,
            @RequestParam String subject,
            @RequestParam String body) {

        emailService.sendEmail(to, subject, body);
        return "✅ Email sent successfully!";
    }

    @PostMapping("/slack")
    public String sendSlack(@RequestParam String message) {
        slackService.sendSlackMessage(message);
        return "Slack message sent!";
    }

    @PostMapping("/sms")
    public ResponseEntity<String> sendTestSms(@RequestParam String to, @RequestParam String message) {
        smsService.sendSms(to, message);
        return ResponseEntity.ok("✅ SMS sent successfully!");
    }

}
