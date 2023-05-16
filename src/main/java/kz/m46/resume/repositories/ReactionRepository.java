package kz.m46.resume.repositories;

import kz.m46.resume.entity.ReactionEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ReactionRepository extends JpaRepository<ReactionEntity, Long> {


    @Query(value = "SELECT * FROM crud.responses WHERE " +
            "resume_id in (select id from crud.resume " +
            "WHERE user_id = :user_id) and user_id != :user_id",
            nativeQuery = true)
    Page<ReactionEntity> findResumeResponses(
            @Param("user_id") Long userId,
            Pageable pageRequest);


    @Query(value = "SELECT * FROM crud.responses WHERE " +
            "resume_id in (select id from crud.resume " +
            "WHERE user_id = :user_id) and user_id = :user_id",
            nativeQuery = true)
    Page<ReactionEntity> findOwnResumeResponses(
            @Param("user_id") Long userId,
            Pageable pageRequest);


    @Query(value = "SELECT * FROM crud.responses WHERE " +
            "vacancy_id in (select id from crud.vacancy " +
            "WHERE user_id = :user_id) and user_id != :user_id",
            nativeQuery = true)
    Page<ReactionEntity> findVacancyResponses(
            @Param("user_id") Long userId,
            Pageable pageRequest);


    @Query(value = "SELECT * FROM crud.responses WHERE " +
            "vacancy_id in (select id from crud.vacancy " +
            "WHERE user_id = :user_id) and user_id = :user_id",
            nativeQuery = true)
    Page<ReactionEntity> findOwnVacancyResponses(
            @Param("user_id") Long userId,
            Pageable pageRequest);


    @Query(value = "SELECT count(*) FROM crud.responses WHERE " +
            "resume_id in (select id from crud.resume WHERE user_id = :user_id) " +
            "and user_id != :user_id and viewed = false",
            nativeQuery = true)
    Integer getNotViewedResumeResponseCount(
            @Param("user_id") Long userId);


    @Query(value = "SELECT count(*) FROM crud.responses WHERE " +
            "vacancy_id in (select id from crud.vacancy WHERE user_id = :user_id) " +
            "and user_id != :user_id and viewed = false",
            nativeQuery = true)
    Integer getNotViewedVacancyResponseCount(
            @Param("user_id") Long userId);
}
