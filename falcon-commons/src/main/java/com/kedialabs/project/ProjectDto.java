package com.kedialabs.project;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import lombok.Data;

@Data
public class ProjectDto {
    @NotEmpty
    private String name;
    private Double lat;
    private Double lng;
    private String description;
    @NotNull
    private ProjectType projectType;
}
