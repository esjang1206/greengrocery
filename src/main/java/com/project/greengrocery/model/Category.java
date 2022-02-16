package com.project.greengrocery.model;

public enum Category {
	FRUIT("FRUIT"),
	VEGETABLE("VEGETABLE");

	private final String value;

	Category(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
}
