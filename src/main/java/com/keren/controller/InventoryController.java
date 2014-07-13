package com.keren.controller;

import java.util.Collection;
import java.util.HashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.RedirectView;
import com.keren.model.Customer;
import com.keren.model.Product;
import com.keren.service.StoreService;

/**
 * This is the controller for inventory.jsp
 * 
 * @author Keren
 * 
 */
@Controller
public class InventoryController {
	@Autowired
	private StoreService storeService;

	private HashMap<String, Customer> customers = new HashMap<String, Customer>();
	private Boolean showAlert = false;

	/**
	 * Init all list variables in jsp
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/inventory", method = RequestMethod.GET)
	public String getInventoryList(ModelMap model) {
		// set all inventory items
		model.addAttribute("inventoryList", storeService.getInventoryListing());
		// show deletion error if needed
		model.addAttribute("deleteAlert", showAlert);

		// init customers
		Collection<Customer> customerList = storeService.getAllCustomers();
		for (Customer cust : customerList) {
			customers.put(cust.getName(), cust);
		}

		model.addAttribute("customerList", customerList);
		
		// go to inventory.jsp
		return "inventory";
	}

	/**
	 * Add a new product to the inventory, or update an existing product
	 * @param product
	 * @param amount
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/inventory/save", method = RequestMethod.POST)
	public View createProduct(@ModelAttribute Product product, Integer amount,
			ModelMap model) {
		// if a product was entered
		if (StringUtils.hasText(product.getName())) {
			// save it
			product.setName(product.getName().trim());
			storeService.addProduct(product, amount);
		}
		
		// no need to show the deletion message
    	showAlert = false;
		
		// reload the page
		return new RedirectView("/Keren/inventory");
	}

	/**
	 * Put an order for a customer
	 * @param customer
	 * @param productAmounts
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/inventory/order", method = RequestMethod.POST)
	public View orderProduct(String customer, String productAmounts,
			ModelMap model) {
		// Checking if there were products ordered
		if (StringUtils.hasText(productAmounts)) {
			// Figure out which product were ordered and their amounts
			String[] tokens = productAmounts.split(",");
			String[] splitPair = null;
			HashMap<String, Integer> productMap = new HashMap<String, Integer>();
			for (String pair : tokens) {
				splitPair = pair.split(":");
				productMap.put(splitPair[0], Integer.parseInt(splitPair[1]));
			}
			// Insert a new order
			storeService.placeOrder(customers.get(customer), productMap);
		}
		
		// no need to show the deletion message
    	showAlert = false;
		
		// reload the page
		return new RedirectView("/Keren/inventory");
	}

	/**
	 * Deletes an item from the inventory
	 * @param product
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/inventory/delete", method = RequestMethod.GET)
	public View deleteProduct(String product, ModelMap model) {
		// try to delete the product
		showAlert = !storeService.removeProduct(product);
		
		return new RedirectView("/Keren/inventory");
	}
}
