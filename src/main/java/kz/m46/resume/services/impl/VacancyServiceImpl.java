package kz.m46.resume.services.impl;


import kz.m46.resume.entity.VacancyEntity;
import kz.m46.resume.exception.GeneralApiServerException;
import kz.m46.resume.models.UserDto;
import kz.m46.resume.models.SearchDto;
import kz.m46.resume.models.VacancyDto;
import kz.m46.resume.models.enums.StatusType;
import kz.m46.resume.repositories.VacancyRepository;
import kz.m46.resume.security.JwtUserContext;
import kz.m46.resume.services.VacancyService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class VacancyServiceImpl implements VacancyService {

    final VacancyRepository vacancyRepository;
    final ModelMapper mapper;
    final NotificationServiceImpl notificationService;
    final JwtUserContext userContext;

    @Override
    public VacancyDto createVacancy(VacancyDto vacancyDto) {

        VacancyEntity vacancyEntity = mapper.map(vacancyDto, VacancyEntity.class);
        return mapper.map(vacancyRepository.save(vacancyEntity), VacancyDto.class);

    }

    @Override
    public VacancyDto getVacancyById(Long id) {

        VacancyEntity vacancyEntity = vacancyRepository.findById(id).orElseThrow(() -> {
            throw new GeneralApiServerException("Вакансия не найдена!", HttpStatus.NOT_FOUND);
        });

        return mapper.map(vacancyEntity, VacancyDto.class);
    }

    @Override
    public VacancyDto updateVacancy(VacancyDto vacancyDto) {

        VacancyEntity vacancyEntity = vacancyRepository.findById(vacancyDto.getId()).orElseThrow(() -> {
            throw new GeneralApiServerException("Вакансия не найдена!", HttpStatus.NOT_FOUND);
        });

        vacancyDto.setUser(mapper.map(vacancyEntity.getUser(), UserDto.class));

        if (!Objects.equals(vacancyEntity.getStatus(), vacancyDto.getStatus())) {
            notificationService.createAndSendMessage(vacancyDto);
        }

        return mapper.map(vacancyRepository.save(mapper.map(vacancyDto, VacancyEntity.class)), VacancyDto.class);
    }

    @Override
    public void deleteVacancy(Long id) {

        VacancyEntity vacancyEntity = vacancyRepository.findById(id).orElseThrow(() -> {
            throw new GeneralApiServerException("Вакансия не найдена!", HttpStatus.NOT_FOUND);
        });

        vacancyRepository.delete(vacancyEntity);
    }

    @Override
    public Page<VacancyDto> getAllVacancies(Pageable pageable, SearchDto searchDto) {

        String title = searchDto.getTitle();
        String location = searchDto.getLocation();
        String experience = searchDto.getExperience();
        StatusType isActive = searchDto.getStatus();

        Page<VacancyEntity> list = vacancyRepository.getAllVacancies(location, title, experience, isActive, pageable);
        return list.map(v -> mapper.map(v, VacancyDto.class));
    }

    @Override
    public Page<VacancyDto> getAllResumesForUser(Pageable pageable, SearchDto searchDto) {

        String title = searchDto.getTitle();
        String location = searchDto.getLocation();
        String experience = searchDto.getExperience();
        StatusType status = searchDto.getStatus();
        Boolean isOwn = searchDto.getIsOwn();

        Page<VacancyEntity> list;

        if (isOwn) {
            list = vacancyRepository.allVacanciesForOwnUser(location, title, experience, status, userContext.getId(),pageable);
        } else {
            list = vacancyRepository.allVacanciesForNotOwnUser(location, title, experience, status, userContext.getId(), pageable);
        }

        return list.map(v -> mapper.map(v, VacancyDto.class));
    }

    @Override
    public Integer getWaitingResumesCount() {
        return vacancyRepository.findAllByStatus(StatusType.WAITING);
    }
}
