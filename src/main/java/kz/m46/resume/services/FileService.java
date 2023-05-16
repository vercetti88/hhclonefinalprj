package kz.m46.resume.services;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface FileService {

    void save(List<MultipartFile> files, Long resumeId);

    Resource getFile(Long id);

    Boolean deleteFile(Long id);
}
