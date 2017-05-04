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
import javax.persistence.PrePersist;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonIgnore;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.kedialabs.batchingplant.ConcreteDispatchVoucherAttribute;
import com.kedialabs.batchingplant.ConcreteMixture;
import com.kedialabs.converters.JsonMapConverter;
import com.kedialabs.domain.BaseDomain;
import com.kedialabs.measurement.MaterialUnit;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "concrete_dispatch_ledger")
@Access(AccessType.FIELD)
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class ConcreteDispatchVoucher extends BaseDomain {
    
    @Column(name = "concrete_type")
    @Enumerated(EnumType.STRING)
    private ConcreteMixture concreteType;
    
    @Column(name = "quantity")
    private Double quantity;
    
    @Column(name = "unit")
    @Enumerated(EnumType.STRING)
    private MaterialUnit unit;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "transport_vehicle_id")
    private VehicleInventory transportVehicle;
    
    @Column(name = "dispatch_time")
    private Timestamp dispatchTime;
    
    @JsonIgnore
    public void setLocation(String location){
        this.getAttributes().put(ConcreteDispatchVoucherAttribute.LOCATION.name(), location);
    }
    
    public String getLocation(){
        return (String)this.getAttributes().get(ConcreteDispatchVoucherAttribute.LOCATION.name());
    }
}
