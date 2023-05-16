package kz.m46.resume.models;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ReactionDto {

    private Long id;
    private VacancyDto vacancy;
    private ResumeDto resume;
    private LocalDateTime createDate = LocalDateTime.now();
}
