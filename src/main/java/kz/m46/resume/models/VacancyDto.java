package kz.m46.resume.models;

import kz.m46.resume.models.enums.StatusType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class VacancyDto extends AbstractDto {
    private Long id;

    private String title;

    private String aboutCompany;

    private String location;

    private String requirements;

    private String salary;

    private String operationMode;

    private String experience;

    private String aboutVacancy;

    private LocalDateTime createDate = LocalDateTime.now();

    private StatusType status;

    private Long userId;



}
