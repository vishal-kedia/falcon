package com.kedialabs.batchingplant.domain;

import java.sql.Timestamp;
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
import com.kedialabs.batchingplant.ConcreteMixture;
import com.kedialabs.converters.JsonMapConverter;
import com.kedialabs.domain.BaseDomain;
import com.kedialabs.measurement.MaterialUnit;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "concrete_dispatch_ledger")
@Access(AccessType.FIELD)
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class ConcreteDispatchVoucher extends BaseDomain {
    public static enum ConcreteDispatchVoucherAttribute {
        LOCATION
    }
    @Enumerated(EnumType.STRING)
    private ConcreteMixture concreteType;
    
    private Double quantity;
    
    @Enumerated(EnumType.STRING)
    private MaterialUnit unit;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "transport_vehicle_id")
    private VehicleInventory transportVehicle;
    
    private Timestamp dispatchTime;
    
    @JsonIgnore
    private Boolean deleted;
    
    @JsonIgnore
    public void setLocation(String location){
        attributes.put(ConcreteDispatchVoucherAttribute.LOCATION.name(), location);
    }
    private String getLocation(){
        return (String)attributes.get(ConcreteDispatchVoucherAttribute.LOCATION.name());
    }
}
