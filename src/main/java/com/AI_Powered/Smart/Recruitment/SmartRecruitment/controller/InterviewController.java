package com.AI_Powered.Smart.Recruitment.SmartRecruitment.controller;

import com.AI_Powered.Smart.Recruitment.SmartRecruitment.entity.Interview;
import com.AI_Powered.Smart.Recruitment.SmartRecruitment.repository.InterviewRepository;
import com.AI_Powered.Smart.Recruitment.SmartRecruitment.service.InterviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/interview")
@RequiredArgsConstructor
public class InterviewController {


    private final InterviewService interviewService;
    private final InterviewRepository interviewRepository;

    @PostMapping("/schedule")
    public ResponseEntity<String> scheduleInterview(
            @RequestParam String candidateName,
            @RequestParam String candidateEmail,
            @RequestParam String recruiterEmail,
            @RequestParam String dateTime,
            @RequestParam String venue) {

        try {
            String calendarLink = interviewService.scheduleInterviewAndNotify(candidateName, candidateEmail,
                    recruiterEmail, dateTime, venue);

            return ResponseEntity.ok("✅ Interview Scheduled! Calendar Invite: " + calendarLink);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("❌ Failed: " + e.getMessage());
        }
    }

    @GetMapping("/interviews")
    public List<Interview> getAllInterviews(){
        return  interviewRepository.findAll();
    }


}
