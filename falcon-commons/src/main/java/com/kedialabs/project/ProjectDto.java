package com.kedialabs.project;

import org.hibernate.validator.constraints.NotEmpty;

import lombok.Data;

@Data
public class ProjectDto {
    @NotEmpty
    private String name;
    private Double lat;
    private Double lng;
    private String description;
    @NotEmpty
    private ProjectType projectType;
}
