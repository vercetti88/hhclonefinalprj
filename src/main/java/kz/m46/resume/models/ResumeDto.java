package kz.m46.resume.models;

import kz.m46.resume.models.enums.StatusType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResumeDto extends AbstractDto {
    private Long id;

    private String title;

    private String education;

    private String location;

    private Integer salary;

    private String skills;

    private String workPlaces;

    private String aboutMe;

    private String languages;

    private LocalDateTime createDate = LocalDateTime.now();

    private StatusType status;

    private List<MultipartFile> multipartFiles;

    private List<FileDto> files;

    private UserDto user;
}
