package kz.m46.resume.services;

import kz.m46.resume.models.SearchDto;
import kz.m46.resume.models.VacancyDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface VacancyService {

    VacancyDto createVacancy(VacancyDto vacancyDto);

    VacancyDto getVacancyById(Long id);

    VacancyDto updateVacancy(VacancyDto vacancyDto);

    void deleteVacancy(Long id);

    Page<VacancyDto> getAllVacancies(Pageable pageable, SearchDto searchDto);

    Page<VacancyDto> getAllResumesForUser(Pageable pageable, SearchDto searchDto);

    Integer getWaitingResumesCount();
}
