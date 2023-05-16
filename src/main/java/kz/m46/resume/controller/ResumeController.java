package kz.m46.resume.controller;

import io.swagger.v3.oas.annotations.Operation;
import kz.m46.resume.models.ResumeDto;
import kz.m46.resume.models.SearchDto;
import kz.m46.resume.services.ResumeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/resume")
public class ResumeController {

    private final ResumeService resumeService;

    @PostMapping
    @PreAuthorize("hasAuthority('CLIENT')")
    @Operation(summary = "Создание резюме")
    public ResponseEntity<ResumeDto> createResume(ResumeDto resume) {
        return ResponseEntity.ok(resumeService.createResume(resume));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('CLIENT', 'ADMIN')")
    @Operation(summary = "Получение резюме по id")
    public ResponseEntity<ResumeDto> getResumeById(@PathVariable Long id) {
        return ResponseEntity.ok(resumeService.getResumeById(id));
    }

    @PutMapping
    @PreAuthorize("hasAnyAuthority('CLIENT', 'ADMIN')")
    @Operation(summary = "Изменение резюме")
    public ResponseEntity<ResumeDto> updateResume(ResumeDto resume) {
        return ResponseEntity.ok(resumeService.updateResume(resume));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('CLIENT', 'ADMIN')")
    @Operation(summary = "Удаление резюме")
    public ResponseEntity<Void> deleteResume(@PathVariable Long id) {
        resumeService.deleteResume(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/all")
    @PreAuthorize("hasAuthority('ADMIN')")
    @Operation(summary = "Получение всех резюме")
    public ResponseEntity<Page<ResumeDto>> getAllResumes(Pageable pageable, @RequestBody SearchDto searchDto) {
        return ResponseEntity.ok(resumeService.getAllResumes(pageable, searchDto));
    }

    @GetMapping("/all_user")
    @PreAuthorize("hasAuthority('CLIENT')")
    @Operation(summary = "Получение всех резюме для пользователя")
    public ResponseEntity<Page<ResumeDto>> getAllResumesForUser(Pageable pageable, @RequestBody SearchDto searchDto) {
        return ResponseEntity.ok(resumeService.getAllResumesForUser(pageable, searchDto));
    }

    @GetMapping("/waiting_count")
    @PreAuthorize("hasAuthority('CLIENT')")
    @Operation(summary = "Получение ожидающих подтверждения резюме")
    public ResponseEntity<Integer> getWaitingResumesCount() {
        return ResponseEntity.ok(resumeService.getWaitingResumesCount());
    }
}
