package kz.m46.resume.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FileDto {
    private Long id;
    private Long resumeId;
    private String fileName;
    private String fileFullName;
    private LocalDateTime createDate = LocalDateTime.now();
}
