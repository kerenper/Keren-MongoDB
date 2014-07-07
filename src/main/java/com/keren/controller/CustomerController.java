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
 * This is the controller for customers.jsp
 * @author Keren
 *
 */
@Controller    
public class CustomerController {  
   
	@Autowired
	private StoreService storeService;
	
    @RequestMapping(value = "/customer", method = RequestMethod.GET)  
	public String getCustomerList(ModelMap model) {  
        model.addAttribute("customerList", storeService.getAllCustomers());  
        return "customers";  
    }  
    
    @RequestMapping(value = "/customer/save", method = RequestMethod.POST)  
	public View createCustomer(@ModelAttribute Customer customer, ModelMap model) {
    	if (StringUtils.hasText(customer.getName())) {
    		customer.setName(customer.getName().trim().toUpperCase());
    		storeService.updateAddCustomer(customer);
    	} 
    	return new RedirectView("/Keren/customer");  
    }
        
    @RequestMapping(value = "/customer/delete", method = RequestMethod.GET)  
	public View deleteCustomer(@ModelAttribute Customer customer, ModelMap model) {  
    	storeService.deleteCustomer(customer);  
        return new RedirectView("/Keren/customer");  
    }    
}
