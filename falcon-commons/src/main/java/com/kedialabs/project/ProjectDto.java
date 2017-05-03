package com.kedialabs.project;

import lombok.Data;

@Data
public class ProjectDto {
    private String name;
    private Double lat;
    private Double lng;
    private String description;
    private ProjectType projectType;
}
