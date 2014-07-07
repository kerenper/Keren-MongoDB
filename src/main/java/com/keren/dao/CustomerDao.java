package com.keren.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.keren.model.Customer;
import com.keren.model.Order;

/**
 * This is the Data Access Object for anything Customer
 * @author Keren
 */
@Repository
public class CustomerDao {
	
	@Autowired
	private MongoTemplate mongoTemplate;
	
	public List<Customer> getAllCustomers() {
		return mongoTemplate.findAll(Customer.class);
	}
	
	public void deleteCustomer(Customer customer) {
		mongoTemplate.remove(customer);
	}
	
	public void updateAddCustomer(Customer customer) {
		if (!mongoTemplate.collectionExists(Customer.class)) {
			mongoTemplate.createCollection(Customer.class);
		}
		mongoTemplate.save(customer);		
	}
}
