package com.kedialabs.batchingplant;

import javax.validation.constraints.NotNull;

import com.kedialabs.measurement.MaterialUnit;

import lombok.Data;

@Data
public class ConcreteDispatchVoucherDto {
    @NotNull
    private ConcreteMixture concreteType;
    @NotNull
    private Double quantity;
    @NotNull
    private MaterialUnit unit;
    @NotNull
    private Long transportVehicleId;
    @NotNull
    private Long dispatchTime;
    private String location;
    private String remark;
    @NotNull
    private Long slump;
}
