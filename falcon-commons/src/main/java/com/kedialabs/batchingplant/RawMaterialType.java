package com.kedialabs.batchingplant;

import com.kedialabs.measurement.Unit;

import lombok.Getter;

@Getter
public enum RawMaterialType {
    DIESEL("Diesel",Unit.LITRE),
    CEMENT("Cement",Unit.KILO_GRAM),
    AGGREGATE_10MM("10mm Aggregate",Unit.KILO_GRAM),
    AGGREGATE_20MM("20mm Aggregate",Unit.KILO_GRAM),
    SAND("Sand",Unit.KILO_GRAM),
    FLYASH("Fly Ash",Unit.KILO_GRAM),
    WATER("Water",Unit.LITRE),
    ADMIXTURE("Admixture",Unit.LITRE);
    
    private final String name;
    private final Unit unit;
    private RawMaterialType(String name,Unit unit) {
        this.name = name;
        this.unit = unit;
    }
}
