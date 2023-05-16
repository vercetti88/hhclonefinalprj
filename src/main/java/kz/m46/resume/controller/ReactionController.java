package kz.m46.resume.controller;

import io.swagger.v3.oas.annotations.Operation;
import kz.m46.resume.models.ReactionDto;
import kz.m46.resume.services.ReactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@PreAuthorize("hasAuthority('CLIENT')")
@RequestMapping("/reaction")
public class ReactionController {

    private final ReactionService reactionService;

    @GetMapping("/resume")
    @Operation(summary = "Получение откликов на резюме")
    public ResponseEntity<Page<ReactionDto>> getResumeResponses(Pageable pageable, @RequestParam(required = false) Boolean isOwn) {
        return ResponseEntity.ok(reactionService.getResumeResponses(pageable, isOwn));
    }

    @GetMapping("/vacancy")
    @Operation(summary = "Получение откликов на вакансии")
    public ResponseEntity<Page<ReactionDto>> getVacancyResponses(Pageable pageable, @RequestParam(required = false) Boolean isOwn) {
        return ResponseEntity.ok(reactionService.getVacancyResponses(pageable, isOwn));
    }

    @PostMapping()
    @Operation(summary = "Создание отклика")
    public ResponseEntity<ReactionDto> createResponse(@RequestParam Long resumeId, @RequestParam Long vacancyId) {
        return ResponseEntity.ok(reactionService.createResponse(resumeId, vacancyId));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удаление отклика")
    public ResponseEntity<Void> deleteResponse(@PathVariable Long id) {
        reactionService.deleteResponse(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    @Operation(summary = "Изменение статуса отлика на 'Просмотрено'")
    public ResponseEntity<Void> setViewed(@PathVariable Long id) {
        reactionService.setViewed(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/resume_count")
    @Operation(summary = "Получение количества не просмотренных откликов по резюме")
    public ResponseEntity<Integer> getNotViewedResumeResponseCount() {
        return ResponseEntity.ok(reactionService.getNotViewedResumeResponseCount());
    }

    @GetMapping("/vacancy_count")
    @Operation(summary = "Получение количества не просмотренных откликов по вакансиям")
    public ResponseEntity<Integer> getNotViewedVacancyResponseCount() {
        return ResponseEntity.ok(reactionService.getNotViewedVacancyResponseCount());
    }
}
