package com.prabhas.model;

public class Employee {
	private int id;
	private String name;
	private String city;
	public int getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	public String getCity() {
		return city;
	}
	public void setId(int id) {
		this.id = id;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public void display() {
		// TODO Auto-generated method stub
		System.out.println("ID:"+id+"\nName:"+name+"\nCity:"+city);
	}
}
