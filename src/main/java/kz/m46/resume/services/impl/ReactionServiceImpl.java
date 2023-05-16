package kz.m46.resume.services.impl;

import kz.m46.resume.entity.ReactionEntity;
import kz.m46.resume.exception.GeneralApiServerException;
import kz.m46.resume.models.ReactionDto;
import kz.m46.resume.repositories.ReactionRepository;
import kz.m46.resume.security.JwtUserContext;
import kz.m46.resume.services.ReactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ReactionServiceImpl implements ReactionService {

    private final JwtUserContext userContext;
    private final ReactionRepository reactionRepository;
    private final ResumeServiceImpl resumeService;
    private final VacancyServiceImpl vacancyService;

    @Override
    public Page<ReactionDto> getResumeResponses(Pageable pageable, Boolean isOwn) {

        Page<ReactionEntity> list;

        if (isOwn)
            list = reactionRepository.findOwnResumeResponses(userContext.getId(), pageable);
        else
            list = reactionRepository.findResumeResponses(userContext.getId(), pageable);


        return list.map(this::mapper);
    }

    @Override
    public Page<ReactionDto> getVacancyResponses(Pageable pageable, Boolean isOwn) {

        Page<ReactionEntity> list;

        if (isOwn)
            list = reactionRepository.findOwnVacancyResponses(userContext.getId(), pageable);
        else
            list = reactionRepository.findVacancyResponses(userContext.getId(), pageable);


        return list.map(this::mapper);
    }

    @Override
    public ReactionDto createResponse(Long resumeId, Long vacancyId) {

        ReactionEntity responseEntity = reactionRepository.save(new ReactionEntity(resumeId, vacancyId, userContext.getId(), LocalDateTime.now()));

        return mapper(responseEntity);
    }

    @Override
    public void deleteResponse(Long id) {

        ReactionEntity reactionEntity = reactionRepository.findById(id).orElseThrow(() -> {
            throw new GeneralApiServerException("Отклик не найден!", HttpStatus.NOT_FOUND);
        });

        reactionRepository.delete(reactionEntity);

    }

    @Override
    public void setViewed(Long id) {

        ReactionEntity reactionEntity = reactionRepository.findById(id).orElseThrow(() -> {
            throw new GeneralApiServerException("Отклик не найден!", HttpStatus.NOT_FOUND);
        });

        reactionEntity.setViewed(true);
        reactionRepository.save(reactionEntity);

    }

    @Override
    public Integer getNotViewedResumeResponseCount() {
        return reactionRepository.getNotViewedResumeResponseCount(userContext.getId());
    }

    @Override
    public Integer getNotViewedVacancyResponseCount() {
        return reactionRepository.getNotViewedVacancyResponseCount(userContext.getId());
    }

    public ReactionDto mapper(ReactionEntity responseEntity) {
        ReactionDto responsesDto = new ReactionDto();
        responsesDto.setId(responseEntity.getId());
        responsesDto.setResume(resumeService.getResumeById(responseEntity.getResumeId()));
        responsesDto.setVacancy(vacancyService.getVacancyById(responseEntity.getVacancyId()));
        return responsesDto;
    }

}
