package kz.m46.resume.services;

import kz.m46.resume.models.ResumeDto;
import kz.m46.resume.models.SearchDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ResumeService {

    ResumeDto createResume(ResumeDto resumeDto);

    ResumeDto getResumeById(Long id);

    Page<ResumeDto> getAllResumes(Pageable pageable, SearchDto searchDto);

    Page<ResumeDto> getAllResumesForUser(Pageable pageable, SearchDto searchDto);

    ResumeDto updateResume(ResumeDto resumeDto);

    void deleteResume(Long id);

    Integer getWaitingResumesCount();
}
