package com.AI_Powered.Smart.Recruitment.SmartRecruitment.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Entity
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class Job {

    @Id
    @GeneratedValue
    private Long id;
    private String title;
    @Column(length = 4000)
    private String description;
    @Column(length = 2000)
    private String requiredSkills;

    @ManyToOne
    @JoinColumn(name = "recruiter_id")
    private User recruiter;

    private Instant createdDate = Instant.now();



}
