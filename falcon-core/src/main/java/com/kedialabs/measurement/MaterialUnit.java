package com.kedialabs.measurement;

import lombok.Getter;

@Getter
public enum MaterialUnit {
    BAG_50_KG("Bag 50Kg","Bag",50,Unit.KILO_GRAM),
    METRIC_TON("Metric Ton","MT",1000,Unit.KILO_GRAM),
    KILO_GRAM("Kg","Kg",1,Unit.KILO_GRAM),
    LITRE("Litre","Ltr",1,Unit.LITRE),
    UNIT("No","No",1,Unit.NA),
    CUBIC_METER("M3","M3",1,Unit.CUBIC_METER);
    private final String name;
    private final String shortName;
    private final Integer noOfUnits;
    private final Unit unit;
    private MaterialUnit(String name,String shortName,Integer noOfUnits,Unit unit) {
        this.name = name;
        this.shortName = shortName;
        this.noOfUnits = noOfUnits;
        this.unit = unit;
    }
}
