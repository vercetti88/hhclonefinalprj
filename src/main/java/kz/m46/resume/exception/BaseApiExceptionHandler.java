package kz.m46.resume.exception;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import kz.m46.resume.models.ApiError;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;


@Slf4j
@RestControllerAdvice
public class BaseApiExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {GeneralApiServerException.class})
    public ResponseEntity<Object> handleGeneralApiServerError(GeneralApiServerException ex,
                                                              HttpServletRequest servletRequest,
                                                              WebRequest request) {

        ApiError apiError = ApiError.builder()
                .status(ex.getHttpStatus())
                .statusCode(ex.getHttpStatus().value())
                .message(ex.getMessage())
                .path(servletRequest.getServletPath())
                .timestamp(LocalDateTime.now())
                .build();

        return handleExceptionInternal(ex, apiError, new HttpHeaders(), apiError.getStatus(), request);
    }

    @ExceptionHandler(value = {ConstraintViolationException.class})
    public ResponseEntity<Object> handleConstraintViolationException(ConstraintViolationException ex,
                                                                     HttpServletRequest servletRequest,
                                                                     WebRequest request) {
        StringBuilder message = new StringBuilder();

        ex.getConstraintViolations()
                .forEach(e -> {
                    message.append(e.getPropertyPath())
                            .append(" - ")
                            .append(e.getMessage())
                            .append(", ");
                });

        ApiError apiError = ApiError.builder()
                .status(HttpStatus.BAD_REQUEST)
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .message(message.toString())
                .path(servletRequest.getServletPath())
                .timestamp(LocalDateTime.now())
                .build();

        return handleExceptionInternal(ex, apiError, new HttpHeaders(), apiError.getStatus(), request);
    }
}
