package kz.m46.resume.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import kz.m46.resume.models.enums.RoleType;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

@Data
public class RoleDto implements GrantedAuthority {

    private RoleType role;

    @JsonIgnore
    @Override
    public String getAuthority() {
        return role.name();
    }
}
