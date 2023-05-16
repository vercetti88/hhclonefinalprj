package kz.m46.resume.services;

import kz.m46.resume.models.ReactionDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ReactionService {

    Page<ReactionDto> getResumeResponses(Pageable pageable, Boolean isOwn);

    Page<ReactionDto> getVacancyResponses(Pageable pageable, Boolean isOwn);

    ReactionDto createResponse(Long resumeId, Long vacancyId);

    void deleteResponse(Long id);

    void setViewed(Long id);

    Integer getNotViewedResumeResponseCount();

    Integer getNotViewedVacancyResponseCount();
}
