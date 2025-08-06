package com.AI_Powered.Smart.Recruitment.SmartRecruitment.service;

import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.scheduling.annotation.Async;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;

    @Async
    public void sendEmail(String to, String subject, String text) {
        try {
            SimpleMailMessage msg = new SimpleMailMessage();
            msg.setTo(to);
            msg.setSubject(subject);
            msg.setText(text);
            mailSender.send(msg);
            log.info("✅ Email sent to {} with subject '{}'", to, subject);
        } catch (Exception e) {
            log.error("❌ Failed to send email to {} - Error: {}", to, e.getMessage());
        }
    }

    public void sendInterviewEmail(
            String to,
            String candidateName,
            String recruiterEmail,
            String dateTime,
            String venue,
            String calendarLink,
            String startTime,
            String endTime
    ) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setTo(to);
            helper.setSubject("📅 Interview Scheduled: " + candidateName);


            String emailContent = "<div style='font-family: Arial, sans-serif; line-height: 1.6;'>" +
                    "<h2>📢 Interview Scheduled</h2>" +
                    "<p>Dear <strong>" + candidateName + "</strong>,</p>" +
                    "<p>Your interview has been scheduled by <strong>" + recruiterEmail + "</strong>.</p>" +
                    "<h3>📌 Interview Details:</h3>" +
                    "<ul>" +
                    "<li><b>Candidate:</b> " + candidateName + "</li>" +
                    "<li><b>Recruiter:</b> " + recruiterEmail + "</li>" +
                    "<li><b>Date:</b> " + dateTime + "</li>" +
                    "<li><b>Start Time:</b> " + startTime + "</li>" +
                    "<li><b>End Time:</b> " + endTime + "</li>" +
                    "<li><b>Venue:</b> " + venue + "</li>" +
                    "</ul>" +
                    "<p>📅 <a href='" + calendarLink + "' style='color: #1a73e8; text-decoration: none;'>Click here to view the event on Google Calendar</a></p>" +
                    "<br>" +
                    "<p>Best regards,<br><b>Smart Recruitment Team</b></p>" +
                    "</div>";

            helper.setText(emailContent, true);

            mailSender.send(message);
            System.out.println("✅ Interview email sent to: " + to);

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("❌ Failed to send interview email to: " + to);
        }
    }

}
