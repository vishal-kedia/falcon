package com.kedialabs.batchingplant;

import java.sql.Timestamp;

import javax.validation.constraints.NotNull;

import com.kedialabs.measurement.MaterialUnit;

import lombok.Data;

@Data
public class MaterialInventoryVoucherDto {
    
    @NotNull
    private RawMaterialType materialType;
    
    @NotNull
    private InventoryType inventoryType;
    
    @NotNull
    private Double quantity;
    
    @NotNull
    private MaterialUnit unit;
    
    private Long supplierId;
    
    private Long transportVehicleId;
    
    private Long recieverVehicleId;
    
    @NotNull
    private Long transactionTime;
    
    private String remark;
    
    private String challanNo;
    
    private String royaltyNo;
    
    private String gateEntryNo;
    
    private Double sourceMeasurement;
    
    private MaterialUnit sourceMeasurementUnit;
    
    private String source;
    
    private Boolean qualityCheckPassed;
}
