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
	
	private Boolean showAlert = false;
	
    @RequestMapping(value = "/customers", method = RequestMethod.GET)  
	public String getCustomerList(ModelMap model) {
    	// set all customers
        model.addAttribute("customerList", storeService.getAllCustomers());
        // show deletion error if needed
 		model.addAttribute("deleteAlert", showAlert);
 		
 		// go to customers.jsp
        return "customers";  
    }  
    
    @RequestMapping(value = "/customers/save", method = RequestMethod.POST)  
	public View createCustomer(@ModelAttribute Customer customer, ModelMap model) {
    	// if a customer was entered
    	if (StringUtils.hasText(customer.getName())) {
    		// save the customer
    		customer.setName(customer.getName().trim());
    		storeService.updateAddCustomer(customer);
    	} 
    	
    	// no need to show the deletion message
    	showAlert = false;
    	
    	// reload the page
    	return new RedirectView("/Keren/customers");  
    }
        
    @RequestMapping(value = "/customers/delete", method = RequestMethod.GET)  
	public View deleteCustomer(@ModelAttribute Customer customer, ModelMap model) {
    	// try to delete the customer
    	showAlert = !storeService.deleteCustomer(customer);
    	
    	// reload the page
        return new RedirectView("/Keren/customers");  
    }    
}
