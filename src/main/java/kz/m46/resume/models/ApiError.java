package kz.m46.resume.models;

import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
@Builder
public class ApiError {

    private HttpStatus status;

    private Integer statusCode;

    private String message;

    private String path;

    private LocalDateTime timestamp;
}
