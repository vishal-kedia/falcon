package com.kedialabs.batchingplant;

import lombok.Getter;

@Getter
public enum InventoryType {
    IN("In"),OUT("Out");
	private final String name;
	private InventoryType(String name) {
		this.name = name;
	}
}
