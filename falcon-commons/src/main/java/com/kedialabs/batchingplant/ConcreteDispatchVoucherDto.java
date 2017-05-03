package com.kedialabs.batchingplant;

import java.sql.Timestamp;

import com.kedialabs.measurement.MaterialUnit;

import lombok.Data;

@Data
public class ConcreteDispatchVoucherDto {
    private ConcreteMixture concreteType;
    private Double quantity;
    private MaterialUnit unit;
    private Long transportVehicleId;
    private Timestamp dispatchTime;
    private String location;
}
