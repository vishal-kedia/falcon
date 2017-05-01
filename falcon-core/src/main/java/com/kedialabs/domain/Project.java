package com.kedialabs.domain;

import java.util.Map;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonIgnore;

import com.google.common.collect.Maps;
import com.kedialabs.converters.JsonMapConverter;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "projects")
@Access(AccessType.FIELD)
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class Project extends BaseDomain {
    private String name;
    private Double lat;
    private Double lng;
    private String description;
    
    @Enumerated(EnumType.STRING)
    private ProjectType projectType;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "contractor_id")
    @JsonIgnore
    private Contractor contractor;
    
    private Boolean deleted;
    
    @Column(length = 10000)
    @Convert(converter = JsonMapConverter.class)
    private Map<String, Object> attributes = Maps.newHashMap();
}
