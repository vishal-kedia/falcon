package com.kedialabs.batchingplant.domain;

import java.sql.Timestamp;
import java.util.Objects;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
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

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Strings;
import com.kedialabs.batchingplant.InventoryType;
import com.kedialabs.batchingplant.MaterialInventoryVoucherAttribute;
import com.kedialabs.batchingplant.RawMaterialType;
import com.kedialabs.domain.BaseDomain;
import com.kedialabs.domain.Project;
import com.kedialabs.domain.Vendor;
import com.kedialabs.measurement.MaterialUnit;

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
    
    @Column(name = "material_type")
    @Enumerated(EnumType.STRING)
    private RawMaterialType materialType;
    
    @Column(name = "inventory_type")
    @Enumerated(EnumType.STRING)
    private InventoryType inventoryType;
    
    @Column(name = "quantity")
    private Double quantity;
    
    @Column(name = "unit") 
    @Enumerated(EnumType.STRING)
    private MaterialUnit unit;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "supplier_id")
    private Vendor supplier;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "transport_vehicle_id")
    private VehicleInventory transportVehicle;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "reciever_vehicle_id")
    private VehicleInventory recieverVehicle;
    
    @Column(name = "transaction_time")
    private Timestamp transactionTime;
    
    @Column(name = "remark")
    private String remark;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "project_id")
    @JsonIgnore
    private Project project;
    
    @JsonIgnore
    public void setChallanNo(String challanNo){
        if(!Strings.isNullOrEmpty(challanNo)){
            this.getAttributes().put(MaterialInventoryVoucherAttribute.CHALLAN_NO.name(), challanNo);
        }else{
            this.getAttributes().remove(MaterialInventoryVoucherAttribute.CHALLAN_NO.name());
        }
    }
    
    @JsonProperty
    public String getChallanNo(){
        return (String)this.getAttributes().get(MaterialInventoryVoucherAttribute.CHALLAN_NO.name());
    }
    
    @JsonIgnore
    public void setRoyaltyNo(String royaltyNo){
        if(!Strings.isNullOrEmpty(royaltyNo)){
            this.getAttributes().put(MaterialInventoryVoucherAttribute.ROYALTY_NO.name(), royaltyNo);
        }else{
            this.getAttributes().remove(MaterialInventoryVoucherAttribute.ROYALTY_NO.name());
        }
    }
    
    @JsonProperty
    public String getRoyaltyNo(){
        return (String)this.getAttributes().get(MaterialInventoryVoucherAttribute.ROYALTY_NO.name());
    }
    
    @JsonIgnore
    public void setGateEntryNo(String gateEntryNo){
        if(!Strings.isNullOrEmpty(gateEntryNo)){
            this.getAttributes().put(MaterialInventoryVoucherAttribute.GATE_ENTRY_NO.name(), gateEntryNo);
        }else{
            this.getAttributes().remove(MaterialInventoryVoucherAttribute.GATE_ENTRY_NO.name());
        }
    }
    
    @JsonProperty
    public String getGateEntryNo(){
        return (String)this.getAttributes().get(MaterialInventoryVoucherAttribute.GATE_ENTRY_NO.name());
    }
    
    @JsonIgnore
    public void setSourceMeasurement(Double sourceMeasurement,MaterialUnit sourceMeasurementUnit){
        Validate.notNull(materialType);
        Validate.isTrue(sourceMeasurementUnit.getUnit() == materialType.getUnit());
        if(Objects.nonNull(sourceMeasurement)){
            Validate.notNull(sourceMeasurementUnit);
            Validate.isTrue(materialType.getUnit().equals(sourceMeasurementUnit.getUnit()));
            this.getAttributes().put(MaterialInventoryVoucherAttribute.SOURCE_MEASUREMENT.name(), sourceMeasurement);
            this.getAttributes().put(MaterialInventoryVoucherAttribute.SOURCE_MEASUREMENT_UNIT.name(), sourceMeasurementUnit.name());
        }else{
            this.getAttributes().remove(MaterialInventoryVoucherAttribute.SOURCE_MEASUREMENT.name());
            this.getAttributes().remove(MaterialInventoryVoucherAttribute.SOURCE_MEASUREMENT_UNIT.name());
        }
        
    }
    
    @JsonProperty
    public Double getSourceMeasurement(){
        return (Double)this.getAttributes().get(MaterialInventoryVoucherAttribute.SOURCE_MEASUREMENT.name());
    }
    
    @JsonProperty
    public MaterialUnit getSourceMeasurementUnit(){
        String materialUnit = (String)this.getAttributes().get(MaterialInventoryVoucherAttribute.SOURCE_MEASUREMENT_UNIT.name());
        if(!Strings.isNullOrEmpty(materialUnit)){
            return MaterialUnit.valueOf(materialUnit);
        }
        return null;
    }
    
    @JsonIgnore
    public void setSource(String source){
        if(!Strings.isNullOrEmpty(source)){
            this.getAttributes().put(MaterialInventoryVoucherAttribute.SOURCE.name(), source);
        }else{
            this.getAttributes().remove(MaterialInventoryVoucherAttribute.SOURCE.name());
        }
    }
    
    @JsonProperty
    public String getSource(){
        return (String)this.getAttributes().get(MaterialInventoryVoucherAttribute.SOURCE.name());
    }
    
    @JsonIgnore
    public void setQalityCheckPassed(Boolean isQualityCheckPassed){
        if(Objects.nonNull(isQualityCheckPassed)){
            this.getAttributes().put(MaterialInventoryVoucherAttribute.QUALITY_CHECK_PASSED.name(), isQualityCheckPassed);
        }else{
            this.getAttributes().remove(MaterialInventoryVoucherAttribute.QUALITY_CHECK_PASSED.name());
        }
    }
    
    @JsonProperty
    public Boolean getQualityCheckPassed(){
        return (Boolean)this.getAttributes().get(MaterialInventoryVoucherAttribute.QUALITY_CHECK_PASSED.name());
    }
    
    @PrePersist
    public void prePersist(){
        Validate.isTrue(materialType.getUnit().equals(unit.getUnit()));
        super.prePersist();
    }
    
}
