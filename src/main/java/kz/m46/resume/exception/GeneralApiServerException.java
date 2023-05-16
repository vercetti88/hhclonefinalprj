package kz.m46.resume.exception;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatus;

@Data
@EqualsAndHashCode(callSuper = true)
public class GeneralApiServerException extends RuntimeException {

    @JsonProperty
    private String message;
    @JsonProperty
    private HttpStatus httpStatus;

    public GeneralApiServerException(String message, HttpStatus httpStatus) {
        super(message);
        this.message = message;
        this.httpStatus = httpStatus;
    }

    @JsonProperty
    @Override
    public String getMessage() {
        return super.getMessage();
    }

}
