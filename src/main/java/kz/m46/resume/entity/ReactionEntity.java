package kz.m46.resume.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "reaction", schema = "crud")
public class ReactionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(name = "vacancy_id")
    private Long vacancyId;

    @NotBlank
    @Column(name = "resume_id")
    private Long resumeId;

    @JsonBackReference
    @ToString.Exclude
    @ManyToOne
    private UserEntity user;

    private Boolean viewed;

    private LocalDateTime createDate;

    public ReactionEntity(@NotBlank Long vacancyId, @NotBlank Long resumeId, Long userId, LocalDateTime createDate) {
        this.resumeId = resumeId;
        this.vacancyId = vacancyId;
        this.user.setId(userId);
        this.createDate = createDate;
    }
}
