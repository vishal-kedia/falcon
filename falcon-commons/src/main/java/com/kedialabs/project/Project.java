package com.kedialabs.project;

import lombok.Data;

@Data
public class Project {
    private String name;
    private Double lat;
    private Double lng;
    private String description;
    private ProjectType projectType;
}