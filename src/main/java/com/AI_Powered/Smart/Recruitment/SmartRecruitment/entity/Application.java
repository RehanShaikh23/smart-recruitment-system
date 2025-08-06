package com.AI_Powered.Smart.Recruitment.SmartRecruitment.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class Application {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    private User candidate;

    @ManyToOne
    private Job job;

    @Enumerated(EnumType.STRING)
    private Status status;
    public enum Status { APPLIED, SHORTLISTED, PENDING, REJECTED }

}
