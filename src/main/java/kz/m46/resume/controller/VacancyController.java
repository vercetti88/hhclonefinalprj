package kz.m46.resume.controller;

import io.swagger.v3.oas.annotations.Operation;
import kz.m46.resume.models.SearchDto;
import kz.m46.resume.models.VacancyDto;
import kz.m46.resume.services.VacancyService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/vacancy")
public class VacancyController {

    private final VacancyService vacancyService;

    @PostMapping
    @PreAuthorize("hasAnyAuthority('CLIENT', 'ADMIN')")
    @Operation(summary = "Создание вакансии")
    public ResponseEntity<VacancyDto> createVacancy(@RequestBody VacancyDto vacancyDto) {
        return ResponseEntity.ok(vacancyService.createVacancy(vacancyDto));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('CLIENT', 'ADMIN')")
    @Operation(summary = "Получение вакансии по id")
    public ResponseEntity<VacancyDto> getVacancyById(@PathVariable Long id) {
        return ResponseEntity.ok(vacancyService.getVacancyById(id));
    }

    @PutMapping
    @PreAuthorize("hasAnyAuthority('CLIENT', 'ADMIN')")
    @Operation(summary = "Изменение вакансии")
    public ResponseEntity<VacancyDto> updateVacancy(@RequestBody VacancyDto vacancyDto) {
        return ResponseEntity.ok(vacancyService.updateVacancy(vacancyDto));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('CLIENT', 'ADMIN')")
    @Operation(summary = "Удаление вакансии")
    public ResponseEntity<Void> deleteVacancy(@PathVariable Long id) {
        vacancyService.deleteVacancy(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/all")
    @PreAuthorize("hasAuthority('ADMIN')")
    @Operation(summary = "Получение всех вакансий")
    public ResponseEntity<Page<VacancyDto>> getAllVacancies(Pageable pageable, SearchDto searchDto) {
        return ResponseEntity.ok(vacancyService.getAllVacancies(pageable, searchDto));
    }

    @GetMapping("/all_user")
    @PreAuthorize("hasAuthority('CLIENT')")
    @Operation(summary = "Получение всех вакансий для пользователя")
    public ResponseEntity<Page<VacancyDto>> getAllResumesForUser(Pageable pageable, @RequestBody SearchDto searchDto) {
        return ResponseEntity.ok(vacancyService.getAllResumesForUser(pageable, searchDto));
    }

    @GetMapping("/waiting_count")
    @PreAuthorize("hasAuthority('CLIENT')")
    @Operation(summary = "Получение ожидающих подтверждения вакансий")
    public ResponseEntity<Integer> getWaitingResumesCount() {
        return ResponseEntity.ok(vacancyService.getWaitingResumesCount());
    }

}
