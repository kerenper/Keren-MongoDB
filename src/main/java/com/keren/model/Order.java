package com.keren.model;

import java.math.BigInteger;
import java.util.Collection;
import java.util.Date;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

@Document(collection = "orders")
public class Order {
	@Id
	private BigInteger id;
	@DateTimeFormat(iso = ISO.DATE_TIME)
	private Date date;
	private Customer customer;
	private Collection<OrderDetails> details;
	@Transient
	private Integer total;
	
	public BigInteger getId() {
		return id;
	}
	
	public void setId(BigInteger id) {
		this.id = id;
	}
	
	public Date getDate() {
		return date;
	}
	
	public void setDate(Date date) {
		this.date = date;
	}
	
	public Customer getCustomer() {
		return customer;
	}
	
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	
	public Collection<OrderDetails> getDetails() {
		return details;
	}
	
	public void setDetails(Collection<OrderDetails> details) {
		this.details = details;
	}

	public Integer getTotal() {
		if (details != null) {
			for (OrderDetails detail : details) {
				total += detail.getProduct().getPrice() * detail.getAmount();
			}	
		}
		return total;
	}
}
