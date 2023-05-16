package kz.m46.resume.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor

@Table(name = "user_", schema = "crud")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    private String name;
    @NotBlank
    private String surname;
    @NotBlank
    private String patronymic;
    @NotBlank
    private String email;
    @NotBlank
    private String password;
    private LocalDate birthDay;

    private LocalDate createDate;

    private Boolean isActive = true;

    @ManyToOne
    @JoinColumn(name = "role")
    private RoleEntity role;

    @JsonManagedReference
    @OneToMany(mappedBy = "user")
    private List<ResumeEntity> resumeList;

    @JsonManagedReference
    @OneToMany(mappedBy = "user")
    private List<VacancyEntity> vacancyList;

}
