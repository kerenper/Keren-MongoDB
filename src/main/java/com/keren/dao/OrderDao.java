package com.keren.dao;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.keren.model.Order;

/**
 * This is the Data Access Object for anything Order
 * @author Keren
 */
@Repository
public class OrderDao {
	
	@Autowired
    private MongoTemplate mongoTemplate;

	/**
	 * This function creates a new order document and its details
	 * @param order
	 */
	public void placeOrder(Order order) {
		if (!mongoTemplate.collectionExists(Order.class)) {
			mongoTemplate.createCollection(Order.class);
		}
		
		mongoTemplate.insert(order);
	}
	
	/**
	 * Gets all order document under the customer
	 * @param customer
	 * @return orders
	 */
	public Collection<Order> findOrdersByCustomer(String customer) {
		Query query = new Query(Criteria.where("customer.$id").is(customer));
		return mongoTemplate.find(query, Order.class);
	}
	
	/**
	 * Checks if there are orders with this customer
	 * @param customer
	 * @return true/false
	 */
	public Boolean ordersByCustomerExist(String customer) {
		Query query = new Query(Criteria.where("customer.$id").is(customer));
		return (mongoTemplate.count(query, Order.class) > 0);
	}
	
	/**
	 * Gets all order documents
	 * @return
	 */
	public Collection<Order> getAllOrders() {
		return mongoTemplate.findAll(Order.class);
	}
	
	/**
	 * Checks if there are orders with this product
	 * @param product
	 */
	public Boolean ordersByProductExist(String product) {
		Query query = new Query(Criteria.where("details.product.$id").is(product));
		return (mongoTemplate.count(query, Order.class) > 0);
	}
}
