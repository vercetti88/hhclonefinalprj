package kz.m46.resume.services.impl;


import kz.m46.resume.entity.FileEntity;
import kz.m46.resume.exception.GeneralApiServerException;
import kz.m46.resume.repositories.FileRepository;
import kz.m46.resume.services.FileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;


@RequiredArgsConstructor
@Slf4j
@Service
public class FileServiceImpl implements FileService {

    @Value("${file.path}")
    private Path root;

    private final FileRepository fileRepository;

    @Override
    @Transactional
    public void save(List<MultipartFile> files, Long resumeId) {

        System.out.println();

        log.info("File path: {}", root);

        files.forEach(file -> {
            try {
                Path path = getPath(root +"/" + file.getOriginalFilename());
                System.out.println("PATH:"+path);
                FileEntity fileEntity = new FileEntity();
                fileEntity.setResumeId(resumeId);
                fileEntity.setFileName(path.getFileName().toString());
                fileEntity.setFileFullName(path.toString());
                fileRepository.save(fileEntity);
                Files.copy(file.getInputStream(), this.root.resolve(path));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

    }

    @Override
    public Resource getFile(Long id) {

        log.info("File path: {}", root);

        try {
            FileEntity fileEntity = fileRepository.findById(id).orElseThrow(() -> {
                throw new GeneralApiServerException("Файл не найден!", HttpStatus.NOT_FOUND);
            });

            Path file = root.resolve(fileEntity.getFileFullName());
            Resource resource = new UrlResource(file.toUri());

            if (resource.exists()) {
                if (resource.isReadable()) {
                    return resource;
                } else {
                    throw new GeneralApiServerException("Ошибка при чтении файла!", HttpStatus.BAD_REQUEST);
                }
            } else {
                throw new GeneralApiServerException("Файл не существует!", HttpStatus.NOT_FOUND);
            }

        } catch (MalformedURLException e) {
            throw new GeneralApiServerException("Ошибка файла!", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public Boolean deleteFile(Long id) {

        log.info("File path: {}", root);

        try {
            FileEntity fileEntity = fileRepository.findById(id).orElseThrow(() -> {
                throw new GeneralApiServerException("Файл не найден!", HttpStatus.NOT_FOUND);
            });

            Path file = root.resolve(fileEntity.getFileFullName());
            fileRepository.delete(fileEntity);
            return Files.deleteIfExists(file);

        } catch (IOException e) {
            throw new GeneralApiServerException("Ошибка файла!", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public Path getPath(String fullFileName) {

        String origFileName = FilenameUtils.getBaseName(fullFileName);
        for (int index = 1; Paths.get(fullFileName).toFile().exists(); index++) {
            String fileName = origFileName + "(" + index + ")." + FilenameUtils.getExtension(fullFileName);
            fullFileName = (root + "\\" + fileName);
        }
        return Paths.get(fullFileName);

    }


}
