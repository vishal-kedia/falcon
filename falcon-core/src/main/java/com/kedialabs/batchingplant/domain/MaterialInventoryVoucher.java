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

import com.google.common.collect.Maps;
import com.kedialabs.converters.JsonMapConverter;
import com.kedialabs.domain.BaseDomain;
import com.kedialabs.domain.Vendor;
import com.kedialabs.measurement.Unit;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "material_inventory_ledger")
@Access(AccessType.FIELD)
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class MaterialInventoryVoucher extends BaseDomain{
    public static enum MaterialInventoryVoucherAttribute{
        VEHICLE_NO,
        CHALLAN_NO,
        ROYALTY_NO,
        GATE_ENTRY_NO,
        SOURCE_MEASUREMENT,
        SOURCE_MEASUREMENT_UNIT,
        SOURCE,
        QUALITY_CHECK_PASSED
    }
    public static enum InventoryType {
        IN,OUT;
    }
    @Enumerated(EnumType.STRING)
    private RawMaterialType materialType;
    
    @Enumerated(EnumType.STRING)
    private InventoryType inventoryType;
    
    private Double quantity;
    
    @Enumerated(EnumType.STRING)
    private Unit unit;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "supplier_id")
    private Vendor supplier;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "transport_vehicle_id")
    private VehicleInventory transportVehicle;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "reciever_vehicle_id")
    private VehicleInventory recieverVehicle;
    
    private Timestamp transactionTime;
    
    private String remark;
    
    private Boolean deleted;
    
    @Column(length = 10000)
    @Convert(converter = JsonMapConverter.class)
    private Map<MaterialInventoryVoucher, Object> attributes = Maps.newHashMap();
    
}
