package com.AI_Powered.Smart.Recruitment.SmartRecruitment.service;

import com.AI_Powered.Smart.Recruitment.SmartRecruitment.dto.*;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.*;

@Service
public class MockAIServiceImpl implements AIService {

    private final Random random = new Random();


    private final Map<String, List<String>> techKeywords = Map.of(
            "spring", Arrays.asList("spring boot", "spring mvc", "spring security", "dependency injection", "microservices"),
            "java", Arrays.asList("java", "jvm", "oop", "collections", "multithreading", "lambda"),
            "microservices", Arrays.asList("microservices", "rest api", "distributed systems", "docker", "kubernetes"),
            "database", Arrays.asList("sql", "mysql", "postgresql", "mongodb", "hibernate", "jpa"),
            "web", Arrays.asList("html", "css", "javascript", "react", "angular", "bootstrap")
    );


    public Mono<ResumeScoreResponse> scoreResume(String resumeText, String jobDescription) {

        return Mono.delay(Duration.ofMillis(500 + random.nextInt(1500)))
                .map(tick -> {
                    int score = calculateMockScore(resumeText, jobDescription);
                    String feedback = generateMockFeedback(score, jobDescription);
                    List<String> strengths = generateMockStrengths(resumeText, jobDescription);
                    List<String> improvements = generateMockImprovements(score);

                    return ResumeScoreResponse.builder()
                            .overallScore(score)
                            .feedback(feedback)
                            .strengths(strengths)
                            .areasForImprovement(improvements)
                            .matchedSkills(extractMatchedSkills(resumeText, jobDescription))
                            .missingSkills(generateMissingSkills(jobDescription))
                            .build();
                });
    }

    @Override
    public Mono<SkillGapResponse> analyzeSkillGaps(String candidateSkills, List<String> requiredSkills) {
        return Mono.delay(Duration.ofMillis(300 + random.nextInt(1000)))
                .map(tick -> {
                    List<String> gaps = findMockSkillGaps(candidateSkills, requiredSkills);
                    List<LearningResource> resources = generateMockLearningResources(gaps);

                    return SkillGapResponse.builder()
                            .skillGaps(gaps)
                            .learningResources(resources)
                            .priorityLevel(gaps.size() > 3 ? "HIGH" : gaps.size() > 1 ? "MEDIUM" : "LOW")
                            .estimatedLearningTime(gaps.size() * 2 + " weeks")
                            .build();
                });
    }

    @Override
    public Mono<BiasAuditResponse> auditBias(List<String> candidateNames, String jobTitle) {
        return Mono.delay(Duration.ofMillis(400 + random.nextInt(800)))
                .map(tick -> {

                    double biasScore = 0.1 + (random.nextDouble() * 0.3); // 0.1 to 0.4
                    List<String> detectedBiases = generateMockBiasDetection();
                    List<String> recommendations = generateMockBiasRecommendations();

                    return BiasAuditResponse.builder()
                            .biasScore(biasScore)
                            .detectedBiases(detectedBiases)
                            .recommendations(recommendations)
                            .isWithinAcceptableRange(biasScore < 0.3)
                            .auditSummary(String.format("Analyzed %d candidates for %s position. Overall bias level: %s",
                                    candidateNames.size(), jobTitle, biasScore < 0.2 ? "LOW" : "MODERATE"))
                            .build();
                });
    }

    private int calculateMockScore(String resumeText, String jobDescription) {
        if (resumeText == null || resumeText.trim().isEmpty()) {
            return 20 + random.nextInt(30);
        }

        String resumeLower = resumeText.toLowerCase();
        String jobLower = jobDescription.toLowerCase();
        int matchCount = 0;

        // Count keyword matches
        for (Map.Entry<String, List<String>> entry : techKeywords.entrySet()) {
            if (jobLower.contains(entry.getKey())) {
                for (String keyword : entry.getValue()) {
                    if (resumeLower.contains(keyword)) {
                        matchCount++;
                    }
                }
            }
        }


        int baseScore = Math.min(85, 40 + (matchCount * 8));


        int variation = random.nextInt(21) - 10;

        return Math.max(15, Math.min(95, baseScore + variation));
    }

    private String generateMockFeedback(int score, String jobDescription) {
        if (score >= 80) {
            return "Excellent match! The candidate's profile strongly aligns with the job requirements for " +
                    jobDescription + ". They demonstrate relevant technical skills and experience.";
        } else if (score >= 65) {
            return "Good match with room for improvement. The candidate shows solid foundation in " +
                    jobDescription + " but could benefit from additional experience in specific areas.";
        } else if (score >= 50) {
            return "Moderate match. The candidate has some relevant skills for " + jobDescription +
                    " but lacks several key competencies. Consider for entry-level positions.";
        } else {
            return "Limited match. The candidate's current skill set doesn't strongly align with " +
                    jobDescription + " requirements. Significant training would be needed.";
        }
    }

    private List<String> generateMockStrengths(String resumeText, String jobDescription) {
        List<String> strengths = new ArrayList<>();
        String resumeLower = resumeText.toLowerCase();

        if (resumeLower.contains("spring") || resumeLower.contains("microservices")) {
            strengths.add("Strong experience with Spring framework and microservices architecture");
        }
        if (resumeLower.contains("java")) {
            strengths.add("Solid Java programming skills and object-oriented design principles");
        }
        if (resumeLower.contains("database") || resumeLower.contains("sql")) {
            strengths.add("Database design and SQL query optimization experience");
        }
        if (resumeLower.contains("api") || resumeLower.contains("rest")) {
            strengths.add("RESTful API development and integration expertise");
        }


        if (strengths.isEmpty()) {
            strengths.addAll(Arrays.asList(
                    "Shows good problem-solving approach",
                    "Demonstrates willingness to learn new technologies",
                    "Has relevant educational background"
            ));
        }

        return strengths.subList(0, Math.min(3, strengths.size()));
    }

    private List<String> generateMockImprovements(int score) {
        List<String> improvements = new ArrayList<>();

        if (score < 70) {
            improvements.add("Gain more hands-on experience with modern development frameworks");
        }
        if (score < 60) {
            improvements.add("Strengthen understanding of software design patterns and architecture");
        }
        if (score < 50) {
            improvements.add("Develop proficiency in version control systems like Git");
        }


        if (improvements.isEmpty()) {
            improvements.add("Consider obtaining relevant certifications to validate technical skills");
        }

        return improvements;
    }

    private List<String> extractMatchedSkills(String resumeText, String jobDescription) {
        List<String> matched = new ArrayList<>();
        String resumeLower = resumeText.toLowerCase();
        String jobLower = jobDescription.toLowerCase();

        if (jobLower.contains("spring") && resumeLower.contains("spring")) {
            matched.add("Spring Framework");
        }
        if (jobLower.contains("java") && resumeLower.contains("java")) {
            matched.add("Java");
        }
        if (jobLower.contains("microservices") && resumeLower.contains("microservices")) {
            matched.add("Microservices");
        }
        if (jobLower.contains("sql") && resumeLower.contains("sql")) {
            matched.add("SQL");
        }

        return matched;
    }

    private List<String> generateMissingSkills(String jobDescription) {
        List<String> missing = new ArrayList<>();
        String jobLower = jobDescription.toLowerCase();

        if (jobLower.contains("spring") && random.nextBoolean()) {
            missing.add("Spring Security");
        }
        if (jobLower.contains("microservices")) {
            missing.add("Docker containerization");
        }
        if (random.nextBoolean()) {
            missing.add("Unit testing with JUnit");
        }

        return missing;
    }

    private List<String> findMockSkillGaps(String candidateSkills, List<String> requiredSkills) {
        List<String> gaps = new ArrayList<>();
        String skillsLower = candidateSkills.toLowerCase();

        for (String required : requiredSkills) {
            if (!skillsLower.contains(required.toLowerCase())) {
                gaps.add(required);
            }
        }


        if (gaps.isEmpty() && !requiredSkills.isEmpty()) {
            gaps.add("Advanced " + requiredSkills.get(0));
        }

        return gaps;
    }

    private List<LearningResource> generateMockLearningResources(List<String> skillGaps) {
        List<LearningResource> resources = new ArrayList<>();

        for (String skill : skillGaps) {
            resources.add(LearningResource.builder()
                    .title("Complete " + skill + " Course")
                    .provider("Online Learning Platform")
                    .type("Video Course")
                    .duration(random.nextInt(20) + 10 + " hours")
                    .difficulty("Intermediate")
                    .url("https://example-learning-platform.com/" + skill.toLowerCase().replace(" ", "-"))
                    .build());
        }

        return resources;
    }

    private List<String> generateMockBiasDetection() {
        List<String> possibleBiases = Arrays.asList(
                "Minimal gender bias detected in candidate evaluation",
                "No significant age-related bias found",
                "Educational background bias within acceptable range"
        );


        Collections.shuffle(possibleBiases);
        return possibleBiases.subList(0, 1 + random.nextInt(2));
    }

    private List<String> generateMockBiasRecommendations() {
        return Arrays.asList(
                "Continue using structured interview processes",
                "Regularly review selection criteria for objectivity",
                "Consider blind resume reviews for initial screening"
        );
    }
}