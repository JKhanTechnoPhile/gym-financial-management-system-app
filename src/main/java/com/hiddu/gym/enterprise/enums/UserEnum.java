package com.hiddu.gym.enterprise.enums;

public enum UserEnum {
	USER_PLATFORM_ADMIN(101, "ROLE_PLATFORM_ADMIN"),
	USER_BRANCH_ADMIN(102, "ROLE_BRANCH_ADMIN"),
	USER_BRANCH_MANAGER(103, "ROLE_BRANCH_MANAGER"),
	USER_CONSUMER(104, "ROLE_CONSUMER");
	
	private int id;
	
	private String name;
	
	UserEnum(int id, String name) {
		this.id = id;
		this.name = name;
	}
	
	public int getId() {
		return this.id;
	}
	
	public String getName() {
		return this.name;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return this.name;
	}
}
