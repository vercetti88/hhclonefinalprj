package kz.m46.resume.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import kz.m46.resume.models.enums.StatusType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "resume", schema = "crud")
public class ResumeEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotBlank
    private String education;
    @NotBlank
    private String location;
    @NotBlank
    private String title;

    private Integer salary;
    @NotBlank
    private String skills;

    private String workPlaces;

    private String experience;
    @NotBlank
    private String aboutMe;
    @NotBlank
    private String languages;

    private LocalDateTime createDate;

    @Enumerated(EnumType.STRING)
    @JsonIgnore
    private StatusType status;

    @OneToMany(fetch = FetchType.EAGER, cascade = {CascadeType.ALL})
    @JoinColumn(name = "resume_id", referencedColumnName = "id")
    private List<FileEntity> files;

    @OneToMany(fetch = FetchType.EAGER, cascade = {CascadeType.ALL})
    @JoinColumn(name = "resume_id", referencedColumnName = "id")
    private List<ReactionEntity> responses;

    @JsonBackReference
    @ToString.Exclude
    @ManyToOne
    private UserEntity user;
}
