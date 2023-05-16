package kz.m46.resume.models;

import lombok.Data;

@Data
public class UserSearchDto {

    private String searchField;
    private Boolean isActive;

}
