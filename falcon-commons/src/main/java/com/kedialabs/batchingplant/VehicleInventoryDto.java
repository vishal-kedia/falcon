package com.kedialabs.batchingplant;

import lombok.Data;

@Data
public class VehicleInventoryDto {
    private String vehicleNo;
    private String description;
    private Long vendorId;
}
