package com.AI_Powered.Smart.Recruitment.SmartRecruitment.runner;

import com.AI_Powered.Smart.Recruitment.SmartRecruitment.entity.Application;
import com.AI_Powered.Smart.Recruitment.SmartRecruitment.entity.CandidateProfile;
import com.AI_Powered.Smart.Recruitment.SmartRecruitment.entity.Job;
import com.AI_Powered.Smart.Recruitment.SmartRecruitment.entity.User;
import com.AI_Powered.Smart.Recruitment.SmartRecruitment.repository.ApplicationRepository;
import com.AI_Powered.Smart.Recruitment.SmartRecruitment.repository.CandidateProfileRepository;
import com.AI_Powered.Smart.Recruitment.SmartRecruitment.repository.JobRepository;
import com.AI_Powered.Smart.Recruitment.SmartRecruitment.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class SeedDataRunner implements CommandLineRunner {

    private final UserRepository userRepo;
    private final JobRepository jobRepo;
    private final CandidateProfileRepository cpRepo;
    private final ApplicationRepository appRepo;
    private final PasswordEncoder encoder;

    @Override
    public void run(String... args) {
        if (userRepo.count() > 0) return;

        User c1 = userRepo.save(User.builder().email("c1@test.com").name("Alice").password(encoder.encode("pass")).role(User.Role.CANDIDATE).build());
        User c2 = userRepo.save(User.builder().email("c2@test.com").name("Bob").password(encoder.encode("pass")).role(User.Role.CANDIDATE).build());
        User r1 = userRepo.save(User.builder().email("r1@test.com").name("Rec").password(encoder.encode("pass")).role(User.Role.RECRUITER).build());

        cpRepo.save(CandidateProfile.builder().user(c1).skills("Java,Spring,SQL").experience(3).build());
        cpRepo.save(CandidateProfile.builder().user(c2).skills("Python,Docker").experience(2).build());

        Job j1 = jobRepo.save(Job.builder().title("Java Dev").description("Spring Boot microservices").requiredSkills("Java,Spring,SQL").recruiter(r1).build());
        Job j2 = jobRepo.save(Job.builder().title("DevOps").description("CI/CD").requiredSkills("Docker,Kubernetes").recruiter(r1).build());

        appRepo.save(Application.builder().candidate(c1).job(j1).status(Application.Status.APPLIED).build());
    }

}
