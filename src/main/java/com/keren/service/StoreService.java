package com.keren.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.keren.dao.*;
import com.keren.model.*;

/**
 * This is the main service of the store
 * @author Keren
 */
@Service
public class StoreService {
	
	@Autowired
	private CustomerDao customerDao;
	
	@Autowired
	private InventoryDao inventoryDao;
	
	@Autowired
	private ProductDao productDao;
	
	@Autowired
	private OrderDao orderDao;
	
	/**
	 * Returns a list of all existing customers
	 */
	public Collection<Customer> getAllCustomers() {
		return customerDao.getAllCustomers();
	}
	
	/**
	 * Delete a customer
	 * @param customer
	 */
	public Boolean deleteCustomer(Customer customer) {
		// if no orders exist - it's safe to delete this customer
		if (!orderDao.ordersByCustomerExist(customer.getName())) {
			customerDao.deleteCustomer(customer);
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Update or add a new customer
	 * @param customer
	 */
	public void updateAddCustomer(Customer customer) {
		customerDao.updateAddCustomer(customer);
	}

	/**
	 * Handles adding a new product to products collection and to inventory
	 * @product - the product in question
	 * @amount - how many items to add to the inventory
	 */
	public void addProduct(Product product, Integer amount) {
		Inventory inv = new Inventory();
		inv.setProduct(product);
		inv.setAmount(amount);
		
		// Update product
		productDao.addUpdateProduct(product);
		
		// Update inventory amount
		inventoryDao.addUpdateInventory(inv);
	}
	
	/**
	 * Deletes a product from the inventory collection
	 * @param inv
	 */
	public Boolean removeProduct(String product) {
		// if no orders exist - it's safe to delete this product
		if (!orderDao.ordersByProductExist(product)) {
			inventoryDao.deleteFromInventory(product);
			productDao.removeProduct(product);
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Handles adding a new order for the customer.
	 * Also, it reduces the amount of the sold items from the inventory.
	 * @customerName - id of the customer document
	 * @productAmounts - a map consisting of product name as keys and amounts for value
	 */
	public void placeOrder(Customer customer,
			HashMap<String, Integer> productAmounts) {
		Product currProduct = null;
		OrderDetails newDetails = null;
		Collection<OrderDetails> details = new ArrayList<OrderDetails>();
		
		// Creating the order
		Order order = new Order();
		order.setCustomer(customer);
		order.setDate(new Date());
		
		// Finding and populating the product, and creating the order details accordingly
		for (String productName : productAmounts.keySet()) {
			currProduct = productDao.findProductByName(productName);
			newDetails = new OrderDetails();
			newDetails.setProduct(currProduct);
			newDetails.setAmount(productAmounts.get(productName));
			
			details.add(newDetails);
			
			inventoryDao.reduceFromInventory(productName, productAmounts.get(productName));
		}
		
		// Setting the details to the order
		order.setDetails(details);
		
		// Add a new order for this customer
		orderDao.placeOrder(order);
	}
	
	/**
	 * Returns all items in the inventory and their amounts
	 */
	public Collection<Inventory> getInventoryListing() {
		return inventoryDao.getInventoryListing();
	}
	
	/**
	 * Returns all orders
	 */
	public Collection<Order> getAllOrders() {
		return orderDao.getAllOrders();
	}
	
	/**
	 * Gets all orders under a specified customer
	 * @param customer - the customer
	 * @return - order list
	 */
	public Collection<Order> findOrdersByCustomer(String customer) {
		return orderDao.findOrdersByCustomer(customer);
	}
}
