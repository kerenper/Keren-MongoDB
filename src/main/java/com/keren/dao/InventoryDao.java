package com.keren.dao;

import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;
import com.keren.model.Customer;
import com.keren.model.Inventory;

/**
 * This is the Data Access Object for anything Inventory
 * @author Keren
 */
@Repository
public class InventoryDao {
	@Autowired
    private MongoTemplate mongoTemplate;

	/**
	 * Adds an entry to the inventory
	 * @param inv
	 */
	public void addUpdateInventory(Inventory inv) {
		if (!mongoTemplate.collectionExists(Customer.class)) {
			mongoTemplate.createCollection(Customer.class);
		}
		
		mongoTemplate.save(inv);
	}

	/**
	 * Removes a specified amount from the inventory
	 * @param productName - the name of the product
	 * @param amount - the amount to be removed
	 */
	public void reduceFromInventory(String productName, Integer amount) {
		Query query = new Query(Criteria.where("product.name").is(productName));
		Update update = new Update().inc("amount", -amount);
		mongoTemplate.findAndModify(query, update, Inventory.class);
	}
	
	/**
	 * Deletes a product from the inventory completely
	 * @param inv
	 */
	public void deleteFromInventory(String product) {
		Query query = new Query(Criteria.where("product.name").is(product));
		mongoTemplate.findAndRemove(query, Inventory.class);
	}

	/**
	 * Gets all items in the inventory
	 * @return
	 */
	public Collection<Inventory> getInventoryListing() {
		return mongoTemplate.findAll(Inventory.class);
	}
}
