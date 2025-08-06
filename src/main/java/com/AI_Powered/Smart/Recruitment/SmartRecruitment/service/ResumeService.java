package com.AI_Powered.Smart.Recruitment.SmartRecruitment.service;

import com.AI_Powered.Smart.Recruitment.SmartRecruitment.entity.CandidateProfile;
import com.AI_Powered.Smart.Recruitment.SmartRecruitment.entity.User;
import com.AI_Powered.Smart.Recruitment.SmartRecruitment.repository.CandidateProfileRepository;
import lombok.RequiredArgsConstructor;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.tika.Tika;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.sax.BodyContentHandler;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class ResumeService {

    private final CandidateProfileRepository repo;
    private final FileStorageService fileService;

    public CandidateProfile uploadResume(MultipartFile file, User user) throws IOException, TikaException {
        CandidateProfile cp = repo.findByUser(user)
                .orElseGet(() -> CandidateProfile.builder().user(user).build());

        String path = fileService.store(file);
        cp.setResumeUrl(path);

        BodyContentHandler handler = new BodyContentHandler();
        Metadata meta = new Metadata();
        ParseContext ctx = new ParseContext();
        Tika tika = new Tika();
        String text = tika.parseToString(file.getInputStream());

        cp.setSkills(extractSkills(text));
        return repo.save(cp);
    }

    public byte[] getResumeByCandidate(Long candidateId) throws IOException {
        CandidateProfile candidate = repo.findById(candidateId)
                .orElseThrow(() -> new RuntimeException("Candidate not found"));

        if (candidate.getResumeUrl() == null) {
            throw new RuntimeException("No resume found for this candidate");
        }

        return fileService.load(candidate.getResumeUrl());
    }

    public CandidateProfile getCandidateProfile(Long candidateId) {
        return repo.findById(candidateId)
                .orElseThrow(() -> new RuntimeException("Candidate not found"));
    }

    public List<CandidateProfile> getAllCandidates() {
        return repo.findAll();
    }

    public List<CandidateProfile> getCandidatesWithResumes() {
        return repo.findAll().stream()
                .filter(candidate -> candidate.getResumeUrl() != null)
                .toList();
    }

    public boolean hasResume(Long candidateId) {
        CandidateProfile candidate = repo.findById(candidateId).orElse(null);
        return candidate != null && candidate.getResumeUrl() != null;
    }

    public void deleteResume(Long candidateId) throws IOException {
        CandidateProfile candidate = repo.findById(candidateId)
                .orElseThrow(() -> new RuntimeException("Candidate not found"));

        if (candidate.getResumeUrl() != null) {
            fileService.delete(candidate.getResumeUrl());
            candidate.setResumeUrl(null);
            candidate.setSkills(null);
            repo.save(candidate);
        }
    }

    private String extractSkill(String text) {
        Set<String> skills = new HashSet<>();
        Matcher m = Pattern.compile("\\b[A-Z][a-z]+").matcher(text);
        while (m.find()) skills.add(m.group());
        return String.join(",", skills);
    }



    public String getResumeText(Long candidateId) throws IOException {
        CandidateProfile candidate = repo.findById(candidateId)
                .orElseThrow(() -> new RuntimeException("Candidate not found"));

        if (candidate.getResumeUrl() == null) {
            throw new RuntimeException("No resume found for this candidate");
        }

        byte[] fileContent = fileService.load(candidate.getResumeUrl());


        try (PDDocument document = PDDocument.load(new ByteArrayInputStream(fileContent))) {
            PDFTextStripper stripper = new PDFTextStripper();
            String text = stripper.getText(document);

            System.out.println("DEBUG: Extracted text length = " + text.length());
            if (text.trim().isEmpty()) {
                System.err.println("WARNING: PDFBox returned empty text.");
            } else {
                System.out.println("DEBUG: First 200 chars: " + (text.length() > 200 ? text.substring(0, 200) : text));
            }

            return text.trim();
        }
    }

    public String getCandidateSkills(Long candidateId) {
        CandidateProfile candidate = repo.findById(candidateId)
                .orElseThrow(() -> new RuntimeException("Candidate not found"));

        return candidate.getSkills() != null ? candidate.getSkills() : "";
    }

    public List<String> getCandidateNames(List<Long> candidateIds) {
        return repo.findAllById(candidateIds)
                .stream()
                .map(candidate -> candidate.getUser().getName())
                .toList();
    }


    private String extractSkills(String text) {
        Set<String> skills = new HashSet<>();


        String[] skillPatterns = {

                "Java", "Python", "JavaScript", "TypeScript", "C\\+\\+", "C#", "PHP", "Ruby", "Go", "Rust",

                "Spring", "React", "Angular", "Vue", "Django", "Flask", "Laravel", "Express",

                "MySQL", "PostgreSQL", "MongoDB", "Redis", "Oracle", "SQLite",

                "AWS", "Azure", "GCP", "Docker", "Kubernetes", "Jenkins", "Git", "CI/CD",

                "HTML", "CSS", "REST", "GraphQL", "Microservices", "Agile", "Scrum"
        };

        for (String skill : skillPatterns) {
            Pattern pattern = Pattern.compile("\\b" + skill + "\\b", Pattern.CASE_INSENSITIVE);
            if (pattern.matcher(text).find()) {
                skills.add(skill);
            }
        }


        Matcher m = Pattern.compile("\\b[A-Z][a-z]+\\b").matcher(text);
        while (m.find()) {
            String potential = m.group();
            if (potential.length() > 2 && potential.length() < 20) {
                skills.add(potential);
            }
        }

        return String.join(",", skills);
    }

}