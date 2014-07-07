package com.keren.controller;

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
import com.keren.service.StoreService;

/**
 * This is the controller for orders.jsp
 * @author Keren
 *
 */
@Controller
public class OrdersController {
	
	@Autowired
	private StoreService storeService;
	
	@RequestMapping(value = "/orders", method = RequestMethod.GET)  
	public String getOrderList(ModelMap model) {  
        model.addAttribute("orderList", storeService.getAllOrders());  
        model.addAttribute("customerList", storeService.getAllCustomers());  
        return "orders";  
    }  
    
    @RequestMapping(value = "/orders/search", method = RequestMethod.POST)  
	public View searchOrder(@ModelAttribute Customer customer, ModelMap model) {
    	if (customer.getName() == "ALL") {
    		model.addAttribute("orderList", storeService.findOrdersByCustomer(customer)); 
    	} else {
    		model.addAttribute("orderList", storeService.getAllOrders()); 
    	}
    	return new RedirectView("/Keren/orders");  
    }
    
}
