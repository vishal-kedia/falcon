package com.kedialabs.domain;

import java.util.Map;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonIgnore;

import com.google.common.collect.Maps;
import com.kedialabs.converters.JsonMapConverter;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "contractors")
@Access(AccessType.FIELD)
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class Contractor extends BaseDomain{
    private String name;
    private String addressLine1;
    private String addressLine2;
    private String city;
    private String pinCode;
    private String phoneNo;
    
    @JsonIgnore
    private Boolean deleted;
    
}
