package com.example.demo.request;

import com.fasterxml.jackson.databind.annotation.EnumNaming;

import jakarta.validation.constraints.Min;

public class Order {
	public String getItem() {
		return item;
	}
	public void setItem(String item) {
		this.item = item;
	}
	public float getPrice() {
		return price;
	}
	public void setPrice(float price) {
		this.price = price;
	}
	private String item;
	@Min(value=1)
	private float price;
	private int quantity;
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public float gettotalAmount() {
		return price*quantity;
	}
	
}
