package com.keren.model;

import org.springframework.data.mongodb.core.mapping.DBRef;

public class OrderDetails {
	@DBRef
	private Product product;
	private Integer amount;
	
	public Product getProduct() {
		return product;
	}
	
	public void setProduct(Product product) {
		this.product = product;
	}
	
	public Integer getAmount() {
		return amount;
	}
	
	public void setAmount(Integer amount) {
		this.amount = amount;
	}
}
