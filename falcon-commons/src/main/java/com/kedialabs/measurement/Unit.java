package com.kedialabs.measurement;

import lombok.Getter;

@Getter
public enum Unit {
    NA("NA"),
    KILO_GRAM("Kg"),
    METER("M"),
    LITRE("Ltr"),
    CUBIC_METER("M3");
    private final String name;
    private Unit(String name) {
        this.name = name;
    }
}