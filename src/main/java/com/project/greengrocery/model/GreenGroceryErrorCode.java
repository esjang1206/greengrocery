package com.project.greengrocery.model;

public enum GreenGroceryErrorCode {
	SUCCESS(0),
	NAME_NULL(1);

	private int code;

	GreenGroceryErrorCode(int code) {
		this.code = code;
	}

	public int getCode() {
		return code;
	}
}

