package com.kedialabs.domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

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
    @Column(name = "name")
    private String name;
    
    @Column(name = "address_line1")
    private String addressLine1;
    
    @Column(name = "address_line2")
    private String addressLine2;
    
    @Column(name = "city")
    private String city;
    
    @Column(name = "pin_code")
    private String pinCode;
    
    @Column(name = "phone_no")
    private String phoneNo;
}
