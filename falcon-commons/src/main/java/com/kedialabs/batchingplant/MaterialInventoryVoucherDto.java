package com.kedialabs.batchingplant;

import java.sql.Timestamp;

import com.kedialabs.measurement.MaterialUnit;
import com.kedialabs.measurement.Unit;

import lombok.Data;

@Data
public class MaterialInventoryVoucherDto {
    private RawMaterialType materialType;
    
    private InventoryType inventoryType;
    
    private Double quantity;
    
    private Unit unit;
    
    private Long supplierId;
    
    private Long transportVehicleId;
    
    private Long recieverVehicleId;
    
    private Timestamp transactionTime;
    
    private String remark;
    
    private String challanNo;
    
    private String royaltyNo;
    
    private String gateEntryNo;
    
    private Double sourceMeasurement;
    
    private MaterialUnit sourceMeasurementUnit;
    
    private String source;
    
    private Boolean qualityCheckPassed;
}
