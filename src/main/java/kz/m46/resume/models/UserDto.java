package kz.m46.resume.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto implements UserDetails {

    private Long id;
    private String name;
    private String surname;
    private String patronymic;
    private String password;
    private String email;
    private LocalDateTime createDate = LocalDateTime.now();
    private RoleDto role;
    private Boolean isActive = true;

    @JsonIgnore
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(role);
    }

    @JsonIgnore
    @Override
    public String getUsername() {
        return email;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return isActive;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return isActive;
    }

    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return isActive;
    }

    @JsonIgnore
    @Override
    public boolean isEnabled() {
        return isActive;
    }
}
