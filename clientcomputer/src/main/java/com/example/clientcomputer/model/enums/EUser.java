package com.example.clientcomputer.model.enums;

public enum EUser {
	ADMIN(1), USER(2);
	
	private int value;

	private EUser(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}
}
