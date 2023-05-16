package kz.m46.resume.controller;

import io.swagger.v3.oas.annotations.Operation;
import kz.m46.resume.services.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@PreAuthorize("hasAnyAuthority('CLIENT', 'ADMIN')")
@RequestMapping("/file")
public class FileController {

    private final FileService fileService;

    @PostMapping("/upload")
    @Operation(summary = "Загрузка файла")
    public ResponseEntity<Void> uploadFile(List<MultipartFile> multipartFiles, Long resumeId) {
        fileService.save(multipartFiles, resumeId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получение файла по id")
    public ResponseEntity<Resource> getFileById(@PathVariable Long id) {
        Resource file = fileService.getFile(id);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
                .body(file);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удаление файла по id")
    public ResponseEntity<Boolean> deleteFileById(@PathVariable Long id) {
        return ResponseEntity.ok(fileService.deleteFile(id));
    }
}
