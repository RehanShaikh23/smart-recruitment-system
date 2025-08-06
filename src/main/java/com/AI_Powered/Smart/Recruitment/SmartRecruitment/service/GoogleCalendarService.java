package com.AI_Powered.Smart.Recruitment.SmartRecruitment.service;

import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.CalendarScopes;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventAttendee;
import com.google.api.services.calendar.model.EventDateTime;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.List;

@Service
public class GoogleCalendarService {

    private final EmailService emailService;
    private static final String APPLICATION_NAME = "SmartRecruitment";
    private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
    private static final List<String> SCOPES = Collections.singletonList(CalendarScopes.CALENDAR);
    private Calendar calendarService;

    public GoogleCalendarService(EmailService emailService) {
        this.emailService = emailService;
    }

    private Calendar getCalendarService() throws IOException, GeneralSecurityException {
        if (calendarService == null) {
            calendarService = buildCalendarService();
        }
        return calendarService;
    }

    private Calendar buildCalendarService() throws IOException, GeneralSecurityException {
        var httpTransport = GoogleNetHttpTransport.newTrustedTransport();
        var in = GoogleCalendarService.class.getResourceAsStream("/google-credentials.json");
        if (in == null) {
            throw new FileNotFoundException("❌ google-credentials.json not found in resources!");
        }
        var clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));
        var flow = new GoogleAuthorizationCodeFlow.Builder(httpTransport, JSON_FACTORY, clientSecrets, SCOPES)
                .setAccessType("offline").build();
        var receiver = new LocalServerReceiver.Builder().setPort(8888).setCallbackPath("/callback").build();
        var credential = new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");

        return new Calendar.Builder(httpTransport, JSON_FACTORY, credential)
                .setApplicationName(APPLICATION_NAME).build();
    }


    public String scheduleInterview(String candidateName, String recruiterEmail, String candidateEmail,
                                    String dateTimeStart, String dateTimeEnd, String location)
            throws IOException, GeneralSecurityException {

        Calendar service = getCalendarService();

        Event event = new Event()
                .setSummary("Interview with " + candidateName)
                .setDescription("Interview scheduled with " + candidateName + " by recruiter.")
                .setLocation(location);

        EventDateTime start = new EventDateTime()
                .setDateTime(new DateTime(dateTimeStart))
                .setTimeZone("Asia/Kolkata");
        event.setStart(start);

        EventDateTime end = new EventDateTime()
                .setDateTime(new DateTime(dateTimeEnd))
                .setTimeZone("Asia/Kolkata");
        event.setEnd(end);

        event.setAttendees(List.of(
                new EventAttendee().setEmail(candidateEmail),
                new EventAttendee().setEmail(recruiterEmail)
        ));

        event = service.events().insert("primary", event).execute();


        emailService.sendInterviewEmail(
                candidateEmail,
                "Your Interview is Scheduled",
                candidateName,
                recruiterEmail,
                dateTimeStart.substring(0, 10),
                dateTimeStart.substring(11, 16),
                location,
                event.getHtmlLink()
        );

        System.out.printf("✅ Interview scheduled: %s\n", event.getHtmlLink());
        return candidateName;
    }
}
