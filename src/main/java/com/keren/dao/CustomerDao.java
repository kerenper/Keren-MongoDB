package com.keren.dao;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;
import com.keren.model.Customer;

/**
 * This is the Data Access Object for anything Customer
 * @author Keren
 */
@Repository
public class CustomerDao {
	
	@Autowired
	private MongoTemplate mongoTemplate;
	
	/**
	 * Get all customer documents
	 * @return
	 */
	public List<Customer> getAllCustomers() {
		return mongoTemplate.findAll(Customer.class);
	}
	
	/**
	 * Removes a customer from the collection
	 * @param customer
	 */
	public void deleteCustomer(Customer customer) {
		mongoTemplate.remove(customer);
	}
	
	/**
	 * Adds an entry to the customer collection
	 * @param inv
	 */
	public void updateAddCustomer(Customer customer) {
		if (!mongoTemplate.collectionExists(Customer.class)) {
			mongoTemplate.createCollection(Customer.class);
		}
		mongoTemplate.save(customer);		
	}
}
