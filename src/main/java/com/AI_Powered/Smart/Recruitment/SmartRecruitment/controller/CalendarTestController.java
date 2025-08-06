package com.AI_Powered.Smart.Recruitment.SmartRecruitment.controller;

import com.AI_Powered.Smart.Recruitment.SmartRecruitment.dto.InterviewRequest;
import com.AI_Powered.Smart.Recruitment.SmartRecruitment.service.GoogleCalendarService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/test")
@RequiredArgsConstructor
public class CalendarTestController {


    private final GoogleCalendarService calendarService;

    @PostMapping("/create")
    @PreAuthorize("hasRole('RECRUITER') or hasRole('ADMIN')")
    public ResponseEntity<String> createEvent() {
        try {
            String calendarLink = calendarService.scheduleInterview(
                    "Rehan Shaikh",                       // Candidate Name
                    "recruiter@test.com",                 // Recruiter Email
                    "candidate@test.com",                 // Candidate Email
                    "2025-08-06T10:00:00+05:30",          // Start Time
                    "2025-08-06T11:00:00+05:30",          // End Time
                    "Google Meet"                         // Venue
            );
            return ResponseEntity.ok("✅ Event created: " + calendarLink);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("❌ Error creating event: " + e.getMessage());
        }
    }

    @PostMapping("/google-calendar")
    @PreAuthorize("hasRole('RECRUITER') or hasRole('ADMIN')")
    public ResponseEntity<String> testGoogleCalendar(@RequestBody InterviewRequest request) {
        try {
            calendarService.scheduleInterview(
                    request.getCandidate(),
                    request.getRecruiterEmail(),
                    request.getCandidateEmail(),
                    request.getStartDateTime(),
                    request.getEndDateTime(),
                    request.getVenue()
            );
            return ResponseEntity.ok("✅ Google Calendar Event Created!");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("❌ Failed to schedule interview: " + e.getMessage());
        }
    }

}





