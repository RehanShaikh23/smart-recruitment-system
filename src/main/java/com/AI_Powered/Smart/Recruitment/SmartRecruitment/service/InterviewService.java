package com.AI_Powered.Smart.Recruitment.SmartRecruitment.service;

import com.AI_Powered.Smart.Recruitment.SmartRecruitment.entity.Interview;
import com.AI_Powered.Smart.Recruitment.SmartRecruitment.repository.InterviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
public class InterviewService {

    private final GoogleCalendarService googleCalendarService;
    private final EmailService emailService;
    private final InterviewRepository interviewRepository;

    public String scheduleInterviewAndNotify(String candidateName, String candidateEmail,
                                             String recruiterEmail, String dateTime, String venue)
            throws Exception {


        String calendarLink = googleCalendarService.scheduleInterview(
                candidateName,
                recruiterEmail,
                candidateEmail,
                "2025-08-06T10:00:00+05:30",
                "2025-08-06T11:00:00+05:30",
                "Google Meet"
        );


        emailService.sendInterviewEmail(
                candidateEmail,
                candidateName,
                recruiterEmail,
                dateTime,
                venue,
                calendarLink,
                "2025-08-06T10:00:00+05:30",
                "2025-08-06T11:00:00+05:30"
        );

        Interview interview = Interview.builder()
                .candidateName(candidateName)
                .candidateEmail(candidateEmail)
                .recruiterEmail(recruiterEmail)
                .venue(venue)
                .calendarLink(calendarLink)
                .startTime(LocalDateTime.parse("2025-08-06T10:00:00", DateTimeFormatter.ISO_LOCAL_DATE_TIME))
                .endTime(LocalDateTime.parse("2025-08-06T11:00:00", DateTimeFormatter.ISO_LOCAL_DATE_TIME))
                .build();

        interviewRepository.save(interview);


        return calendarLink;
    }
}
