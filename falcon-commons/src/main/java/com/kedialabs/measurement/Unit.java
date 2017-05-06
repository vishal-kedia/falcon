package com.kedialabs.measurement;

import lombok.Getter;

@Getter
public enum Unit {
    NA("NA","NA"),
    KILO_GRAM("Kg","weight"),
    METER("M","length"),
    LITRE("Ltr","liquid volume"),
    CUBIC_METER("M3","volume");
    private final String name;
    private final String type;
    private Unit(String name,String type) {
        this.name = name;
        this.type = type;
    }
}