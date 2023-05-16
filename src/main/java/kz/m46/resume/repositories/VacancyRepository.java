package kz.m46.resume.repositories;

import kz.m46.resume.entity.VacancyEntity;
import kz.m46.resume.models.enums.StatusType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface VacancyRepository extends JpaRepository<VacancyEntity, Long> {

    @Query(value = "SELECT * FROM crud.vacancy vc WHERE " +
            "(:location is null or lower(vc.location) like lower(concat('%',:location,'%')))" +
            "and (:title is null or lower(vc.title) like lower(concat('%',:title,'%')))" +
            "and (:experience is null or vc.experience = :experience)" +
            "and (:status is null or vc.status = :status)",
            nativeQuery = true)
    Page<VacancyEntity> getAllVacancies(
            @Param("location") String location,
            @Param("title") String title,
            @Param("experience") String experience,
            @Param("status") StatusType status,
            Pageable pageRequest);

    @Query(value = "SELECT * FROM crud.vacancy vc WHERE " +
            "(:location is null or lower(vc.location) like lower(concat('%',:location,'%')))" +
            "and (:title is null or lower(vc.title) like lower(concat('%',:title,'%')))" +
            "and (:experience is null or vc.experience = :experience)" +
            "and (:status is null or vc.status = :status)" +
            "and (:user_id is null or vc.user_id = :user_id)",
            nativeQuery = true)
    Page<VacancyEntity> allVacanciesForOwnUser(
            @Param("location") String location,
            @Param("title") String title,
            @Param("experience") String experience,
            @Param("status") StatusType status,
            @Param("user_id") Long userId,
            Pageable pageRequest);

    @Query(value = "SELECT * FROM crud.vacancy vc WHERE " +
            "(:location is null or lower(vc.location) like lower(concat('%',:location,'%')))" +
            "and (:title is null or lower(vc.title) like lower(concat('%',:title,'%')))" +
            "and (:experience is null or vc.experience = :experience)" +
            "and (:status is null or vc.status = :status)" +
            "and (:user_id is null or vc.user_id != :user_id)",
            nativeQuery = true)
    Page<VacancyEntity> allVacanciesForNotOwnUser(
            @Param("location") String location,
            @Param("title") String title,
            @Param("experience") String experience,
            @Param("status") StatusType status,
            @Param("user_id") Long userId,
            Pageable pageRequest);

    Integer findAllByStatus(StatusType status);
}
