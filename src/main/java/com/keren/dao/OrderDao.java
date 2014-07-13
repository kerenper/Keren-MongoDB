package com.keren.dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import com.keren.model.Customer;
import com.keren.model.Order;
import com.keren.model.OrderDetails;
import com.keren.model.Product;

/**
 * This is the Data Access Object for anything Order
 * @author Keren
 */
@Repository
public class OrderDao {
	
	@Autowired
    private MongoTemplate mongoTemplate;
	
	@Autowired
    private ProductDao productDao;

	/**
	 * This function creates a new order document and its details
	 * @param customer - the customer for which the order is made
	 * @param productAmounts - the product and amount for each product
	 */
	public void placeOrder(Customer customer,
			HashMap<String, Integer> productAmounts) {
		if (!mongoTemplate.collectionExists(Order.class)) {
			mongoTemplate.createCollection(Order.class);
		}
		
		// Creating the order
		Order order = new Order();
		order.setCustomer(customer);
		order.setDate(new Date());
		
		Product currProduct = null;
		OrderDetails newDetails = null;
		Collection<OrderDetails> details = new ArrayList<OrderDetails>();
		
		// Finding and populating the product, and creating the order details accordingly
		for (String productName : productAmounts.keySet()) {
			currProduct = productDao.findProductByName(productName);
			newDetails = new OrderDetails();
			newDetails.setProduct(currProduct);
			newDetails.setAmount(productAmounts.get(productName));
			
			details.add(newDetails);
		}
		
		// Setting the details to the order
		order.setDetails(details);
		
		// Presto
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
	 * Gets all order documents
	 * @return
	 */
	public Collection<Order> getAllOrders() {
		return mongoTemplate.findAll(Order.class);
	}
}
