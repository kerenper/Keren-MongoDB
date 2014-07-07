package com.keren.controller;

import java.util.Collection;
import java.util.HashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
 * @author Keren
 *
 */
@Controller 
public class InventoryController {
	private static final Logger logger =  LoggerFactory.getLogger(InventoryController.class);
	
	@Autowired
	private StoreService storeService;
	
	private HashMap<String, Customer> customers = new  HashMap<String, Customer>();
	
//	@RequestMapping(value="/inventory")  
//    private ModelAndView selectTag() {  
//        ModelAndView mav = new ModelAndView("customers");  
//        mav.addObject("customerList", storeService.getAllCustomers());  
//        return mav;  
//    }  
	
	@RequestMapping(value = "/inventory", method = RequestMethod.GET)  
	public String getInventoryList(ModelMap model) {  
        model.addAttribute("inventoryList", storeService.getInventoryListing());
        
        Collection<Customer> customerList = storeService.getAllCustomers();
        for (Customer cust : customerList) {
        	customers.put(cust.getName(), cust);
        }
        
        model.addAttribute("customerList", customerList); 
//        model.addAttribute("customerList", customerList);  
        return "inventory";  
    }  
    
    @RequestMapping(value = "/inventory/save", method = RequestMethod.POST)  
	public View createProduct(@ModelAttribute Product product, Integer amount, ModelMap model) {
    	if (StringUtils.hasText(product.getName())) {
    		product.setName(product.getName().trim().toUpperCase());
    		storeService.addProduct(product, amount);
    	} 
    	return new RedirectView("/Keren/inventory");  
    }
    
    @RequestMapping(value = "/inventory/order", method = RequestMethod.POST)  
	public View orderProduct(String customer, HashMap<String, Integer> productAmounts, ModelMap model) {
    	if (customer != null) {
    		System.out.println(customer);
    		logger.info(customer);
    		System.out.println(customer);
    	}
    	for (String key : productAmounts.keySet()) {
    		System.out.println(key + ": " + productAmounts.get(key));
    		logger.info(key + ": " + productAmounts.get(key));
    		System.out.println(key + ": " + productAmounts.get(key));
    	}
    	storeService.placeOrder(customers.get(customer), productAmounts);
        return new RedirectView("/Keren/inventory");  
    }  
        
    @RequestMapping(value = "/inventory/delete", method = RequestMethod.GET)  
	public View deleteProduct(String product, ModelMap model) {  
    	storeService.removeProduct(product);  
        return new RedirectView("/Keren/inventory");  
    }    
}
