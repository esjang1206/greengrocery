package com.project.greengrocery.model;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class AjaxModel {
	@Getter
	private final boolean success;
	@Getter
	private final int errorCode;
	@Getter
	private final Object data;

	public AjaxModel(boolean success, int errorCode) {
		this.success = success;
		this.data = null;
		this.errorCode = errorCode;
	}

	public AjaxModel(boolean success, Object data) {
		this.success = success;
		this.data = data;
		this.errorCode = 0;
	}
}
