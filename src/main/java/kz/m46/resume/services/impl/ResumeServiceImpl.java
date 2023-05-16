package kz.m46.resume.services.impl;


import kz.m46.resume.entity.ResumeEntity;
import kz.m46.resume.exception.GeneralApiServerException;
import kz.m46.resume.models.UserDto;
import kz.m46.resume.models.ResumeDto;
import kz.m46.resume.models.SearchDto;
import kz.m46.resume.models.enums.StatusType;
import kz.m46.resume.repositories.ResumeRepository;
import kz.m46.resume.security.JwtUserContext;
import kz.m46.resume.services.FileService;
import kz.m46.resume.services.ResumeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
@Slf4j
@RequiredArgsConstructor
public class ResumeServiceImpl implements ResumeService {

    private final ResumeRepository resumeRepository;
    private final FileService fileService;try
    private final ModelMapper mapper;
    private final JwtUserContext userContext;
    private final NotificationServiceImpl notificationService;

    @Transactional
    @Override
    public ResumeDto createResume(ResumeDto resumeDto) {

        resumeDto.setStatus(StatusType.WAITING);
        resumeDto.setUser(mapper.map(userContext, UserDto.class));

        ResumeEntity resumeEntity = resumeRepository.save(mapper.map(resumeDto, ResumeEntity.class));

        if (Objects.nonNull(resumeDto.getMultipartFiles())) {
            fileService.save(resumeDto.getMultipartFiles(), resumeEntity.getId());
        }

        log.info("Resume created successfully");

        return mapper.map(resumeEntity, ResumeDto.class);
    }

    @Override
    public ResumeDto getResumeById(Long id) {

        ResumeEntity resumeEntity = resumeRepository.findById(id).orElseThrow(() -> {
            throw new GeneralApiServerException("Резюме не найдено!", HttpStatus.NOT_FOUND);
        });

        return mapper.map(resumeEntity, ResumeDto.class);

    }

    @Override
    public Page<ResumeDto> getAllResumes(Pageable pageable, SearchDto searchDto) {

        String title = searchDto.getTitle();
        String location = searchDto.getLocation();
        String experience = searchDto.getExperience();
        StatusType status = searchDto.getStatus();

        Page<ResumeEntity> list = resumeRepository.getAllResumes(location, title, experience, status, pageable);
        return list.map(v -> mapper.map(v, ResumeDto.class));
    }

    @Override
    public Page<ResumeDto> getAllResumesForUser(Pageable pageable, SearchDto searchDto) {

        String title = searchDto.getTitle();
        String location = searchDto.getLocation();
        String experience = searchDto.getExperience();
        StatusType status = searchDto.getStatus();
        Boolean isOwn = searchDto.getIsOwn();

        Page<ResumeEntity> list;

        if (isOwn)
            list = resumeRepository.allResumesForOwnUser(location, title, experience, status, userContext.getId(), pageable);
        else
            list = resumeRepository.allResumesForNotOwnUser(location, title, experience, status, userContext.getId(), pageable);


        return list.map(v -> mapper.map(v, ResumeDto.class));
    }

    @Override
    public ResumeDto updateResume(ResumeDto resumeDto) {

        ResumeEntity resumeEntity = resumeRepository.findById(resumeDto.getId()).orElseThrow(() -> {
            throw new GeneralApiServerException("Резюме не найдено!", HttpStatus.NOT_FOUND);
        });

        resumeDto.setUser(mapper.map(resumeEntity.getUser(), UserDto.class));

        if (!Objects.equals(resumeEntity.getStatus(), resumeDto.getStatus())) {
            notificationService.createAndSendMessage(resumeDto);
        }

        return mapper.map(resumeRepository.save(mapper.map(resumeDto, ResumeEntity.class)), ResumeDto.class);
    }

    @Override
    public void deleteResume(Long id) {

        ResumeEntity resumeEntity = resumeRepository.findById(id).orElseThrow(() -> {
            throw new GeneralApiServerException("Резюме не найдено!", HttpStatus.NOT_FOUND);
        });

        if (Objects.nonNull(resumeEntity.getFiles())) {
            resumeEntity.getFiles().forEach(file -> fileService.deleteFile(file.getId()));
        }

        resumeRepository.delete(resumeEntity);

    }


    @Override
    public Integer getWaitingResumesCount() {
        return resumeRepository.findAllByStatus(StatusType.WAITING);
    }
}


