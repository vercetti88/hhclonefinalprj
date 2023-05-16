package kz.m46.resume.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import kz.m46.resume.models.enums.RoleType;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

@Data
@NoArgsConstructor
@Entity
@Table(name = "roles", schema = "crud")
public class RoleEntity{

    @Id
    @Enumerated(EnumType.STRING)
    @JsonIgnore
    private RoleType role;

    public RoleEntity(RoleType role) {
        this.role = role;
    }
}
