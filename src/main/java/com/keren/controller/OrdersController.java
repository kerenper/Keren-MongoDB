package com.keren.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.keren.service.StoreService;

/**
 * This is the controller for orders.jsp
 * @author Keren
 *
 */
@Controller
public class OrdersController {
	private final String ALL_CUSTOMERS = "ALL";
	@Autowired
	private StoreService storeService;

	@RequestMapping(value = "/orders", method = RequestMethod.GET)  
	public String getOrderList(String customer, ModelMap model) {  
		if (StringUtils.hasText(customer)) {
    		model.addAttribute("orderList", storeService.findOrdersByCustomer(customer));
    		model.addAttribute("selectedCustomer", customer);
    	} else {
    		model.addAttribute("orderList", storeService.getAllOrders());
    		model.addAttribute("selectedCustomer", ALL_CUSTOMERS);
    	}
    	
    	model.addAttribute("customerList", storeService.getAllCustomers());
        
        return "orders";  
    }  
}
