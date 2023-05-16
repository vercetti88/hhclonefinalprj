package kz.m46.resume.repositories;

import kz.m46.resume.entity.ResumeEntity;
import kz.m46.resume.models.enums.StatusType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ResumeRepository extends JpaRepository<ResumeEntity, Long> {

    @Query(value = "SELECT * FROM crud.resume rs WHERE " +
            "(:location is null or lower(rs.location) like lower(concat('%',:location,'%')))" +
            "and (:title is null or lower(rs.title) like lower(concat('%',:title,'%')))" +
            "and (:experience is null or rs.experience = :experience)" +
            "and (:status is null or rs.status = :status)",
            nativeQuery = true)
    Page<ResumeEntity> getAllResumes(
            @Param("location") String location,
            @Param("title") String title,
            @Param("experience") String experience,
            @Param("status") StatusType status,
            Pageable pageRequest);

    @Query(value = "SELECT * FROM crud.resume rs WHERE " +
            "(:location is null or lower(rs.location) like lower(concat('%',:location,'%')))" +
            "and (:title is null or lower(rs.title) like lower(concat('%',:title,'%')))" +
            "and (:experience is null or rs.experience = :experience)" +
            "and (:status is null or rs.status = :status)" +
            "and (:user_id is null or rs.user_id = :user_id)",
            nativeQuery = true)
    Page<ResumeEntity> allResumesForOwnUser(
            @Param("location") String location,
            @Param("title") String title,
            @Param("experience") String experience,
            @Param("status") StatusType status,
            @Param("user_id") Long userId,
            Pageable pageRequest);

    @Query(value = "SELECT * FROM crud.resume rs WHERE " +
            "(:location is null or lower(rs.location) like lower(concat('%',:location,'%')))" +
            "and (:title is null or lower(rs.title) like lower(concat('%',:title,'%')))" +
            "and (:experience is null or rs.experience = :experience)" +
            "and (:status is null or rs.status = :status)" +
            "and (:user_id is null or rs.user_id != :user_id)",
            nativeQuery = true)
    Page<ResumeEntity> allResumesForNotOwnUser(
            @Param("location") String location,
            @Param("title") String title,
            @Param("experience") String experience,
            @Param("status") StatusType status,
            @Param("user_id") Long userId,
            Pageable pageRequest);

    Integer findAllByStatus(StatusType status);

}
