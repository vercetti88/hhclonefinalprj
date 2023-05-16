package kz.m46.resume.models;

import kz.m46.resume.models.enums.StatusType;
import lombok.Data;

@Data
public class SearchDto {

    private String title;

    private String location;

    private String experience;

    private Boolean isOwn;

    private StatusType status;

}
