package com.kedialabs.batchingplant.domain;

import java.sql.Timestamp;
import java.util.Map;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;

import org.apache.commons.lang3.Validate;
import org.codehaus.jackson.annotate.JsonIgnore;

import com.google.common.base.Strings;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.kedialabs.batchingplant.InventoryType;
import com.kedialabs.batchingplant.MaterialInventoryVoucherAttribute;
import com.kedialabs.batchingplant.RawMaterialType;
import com.kedialabs.converters.JsonMapConverter;
import com.kedialabs.domain.BaseDomain;
import com.kedialabs.domain.Vendor;
import com.kedialabs.measurement.MaterialUnit;
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
    
    @Convert(converter = JsonMapConverter.class)
    @JsonIgnore
    protected Map<String, Object> attributes = Maps.newHashMap();
    
    @JsonIgnore
    public void setChallanNo(String challanNo){
        attributes.put(MaterialInventoryVoucherAttribute.CHALLAN_NO.name(), challanNo);
    }
    
    public String getChallanNo(){
        return (String)attributes.get(MaterialInventoryVoucherAttribute.CHALLAN_NO.name());
    }
    
    @JsonIgnore
    public void setRoyaltyNo(String royaltyNo){
        attributes.put(MaterialInventoryVoucherAttribute.ROYALTY_NO.name(), royaltyNo);
    }
    
    public String getRoyaltyNo(){
        return (String)attributes.get(MaterialInventoryVoucherAttribute.ROYALTY_NO.name());
    }
    
    @JsonIgnore
    public void setGateEntryNo(String gateEntryNo){
        attributes.put(MaterialInventoryVoucherAttribute.GATE_ENTRY_NO.name(), gateEntryNo);
    }
    
    public String getGateEntryNo(){
        return (String)attributes.get(MaterialInventoryVoucherAttribute.GATE_ENTRY_NO.name());
    }
    
    @JsonIgnore
    public void setSourceMeasurement(Double sourceMeasurement,MaterialUnit sourceMeasurementUnit){
        Validate.notNull(sourceMeasurement);
        Validate.notNull(sourceMeasurementUnit);
        Validate.notNull(materialType);
        Validate.isTrue(sourceMeasurementUnit.getUnit() == materialType.getUnit());
        attributes.put(MaterialInventoryVoucherAttribute.SOURCE_MEASUREMENT.name(), sourceMeasurement);
        attributes.put(MaterialInventoryVoucherAttribute.SOURCE_MEASUREMENT_UNIT.name(), sourceMeasurementUnit.getName());
    }
    
    public Double getSourceMeasurement(){
        return (Double)attributes.get(MaterialInventoryVoucherAttribute.SOURCE_MEASUREMENT.name());
    }
    
    public MaterialUnit getSourceMeasurementUnit(){
        String materialUnit = (String)attributes.get(MaterialInventoryVoucherAttribute.SOURCE_MEASUREMENT_UNIT.name());
        if(!Strings.isNullOrEmpty(materialUnit)){
            return MaterialUnit.valueOf(materialUnit);
        }
        return null;
    }
    
    @JsonIgnore
    public void setSource(String source){
        attributes.put(MaterialInventoryVoucherAttribute.SOURCE.name(), source);
    }
    
    public String getSource(){
        return (String)attributes.get(MaterialInventoryVoucherAttribute.SOURCE.name());
    }
    
    @JsonIgnore
    public void setQalityCheckPassed(Boolean isQualityCheckPassed){
        attributes.put(MaterialInventoryVoucherAttribute.QUALITY_CHECK_PASSED.name(), isQualityCheckPassed);
    }
    
    public Boolean getQualityCheckPassed(){
        return (Boolean)attributes.get(MaterialInventoryVoucherAttribute.QUALITY_CHECK_PASSED.name());
    }
    
    @PrePersist
    public void prePersist() {
        attributes = ImmutableMap.<String, Object>copyOf(attributes);
        super.prePersist();
    }
}
