package kz.m46.resume.models;

import kz.m46.resume.models.enums.StatusType;
import lombok.Data;

import java.io.Serializable;

@Data
public abstract class AbstractDto implements Serializable {

    private Long id;
    private StatusType status;
    private String title;
    private UserDto user;

}
