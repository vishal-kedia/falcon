package com.kedialabs.domain;

import java.sql.Timestamp;
import java.util.Map;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import org.activejpa.entity.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.kedialabs.converters.JsonMapConverter;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@MappedSuperclass
@Access(AccessType.FIELD)
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class BaseDomain extends Model {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "created_at")
    @JsonIgnore
    private Timestamp createdAt;
    
    @Column(name = "updated_at")
    @JsonIgnore
    private Timestamp updatedAt;
    
    @Column(name = "created_by")
    @JsonIgnore
    private String createdBy;
    
    @Column(name = "updated_by")
    @JsonIgnore
    private String updatedBy;
    
    @Column(name = "deleted")
    @JsonIgnore
    private Boolean deleted = Boolean.FALSE;
    
    @Column(name = "attributes")
    @Convert(converter = JsonMapConverter.class)
    @JsonIgnore
    private Map<String, Object> attributes = Maps.newHashMap();
    
    @PrePersist
    public void prePersist() {
        attributes = ImmutableMap.<String, Object>copyOf(attributes);
        Timestamp currentTime = new Timestamp(System.currentTimeMillis());
        setCreatedAt(currentTime);
        setUpdatedAt(currentTime);
    }
    
    @PreUpdate
    public void preUpdate() {
        setUpdatedAt(new Timestamp(System.currentTimeMillis()));
    }
    
}
