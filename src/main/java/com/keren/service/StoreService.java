package com.keren.service;

import java.util.Collection;
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
	public void deleteCustomer(Customer customer) {
		customerDao.deleteCustomer(customer);
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
	 * Removes a product from the product collection and deletes it from the inventory completely
	 * @param inv
	 */
	public void removeProduct(String product) {
		inventoryDao.deleteFromInventory(product);
		productDao.deleteProduct(product);
	}

	/**
	 * Handles adding a new order for the customer.
	 * Also, it reduces the amount of the sold items from the inventory.
	 * @customerName - id of the customer document
	 * @productAmounts - a map consisting of product name as keys and amounts for value
	 */
	public void placeOrder(Customer customer,
			HashMap<String, Integer> productAmounts) {
		// Add a new order for this customer
		orderDao.placeOrder(customer, productAmounts);
		
		// For every product id, remove the desired amount from the inventory
		for (String productId : productAmounts.keySet()) {
			System.out.println(productId);
			System.out.println(productAmounts.get(productId));
			inventoryDao.reduceFromInventory(productId, productAmounts.get(productId));
		}
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
	public Collection<Order> findOrdersByCustomer(Customer customer) {
		return orderDao.findOrdersByCustomer(customer);
	}
}
