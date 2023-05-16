package kz.m46.resume.security;

import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class JwtUserContext {

    private Long id;
    private String email;

}
