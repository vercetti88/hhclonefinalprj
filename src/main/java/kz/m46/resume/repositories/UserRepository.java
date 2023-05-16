package kz.m46.resume.repositories;

import kz.m46.resume.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findByEmail(String username);

    Boolean existsByEmail(String email);

    @Query(value = "SELECT * FROM crud.user_ as du "
            + "where lower(du.name) like lower(concat('%', :search_field, '%')) "
            + "and (:is_active is null or du.is_active = :is_active) "
            + "or lower(du.surname) like lower(concat('%', :search_field, '%')) "
            + "and (:is_active is null or du.is_active = :is_active) "
            + "or lower(du.patronymic) like lower(concat('%', :search_field, '%')) "
            + "and (:is_active is null or du.is_active = :is_active) "
            + "or lower(du.email) like lower(concat('%', :search_field,'%'))"
            + "and (:is_active is null or du.is_active = :is_active) ",
            nativeQuery = true)
    Page<UserEntity> findByParams(
            @Param("search_field") String searchField,
            @Param("is_active") Boolean isActive,
            Pageable pageRequest);
}
