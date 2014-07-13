package com.keren.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import com.keren.model.Product;

/**
 * This is the Data Access Object for anything Product
 * @author Keren
 */
@Repository
public class ProductDao {
	
	@Autowired
    private MongoTemplate mongoTemplate;
	
	/**
	 * Adds or updates a product
	 * @param product
	 */
	public void addUpdateProduct(Product product) {
		if (!mongoTemplate.collectionExists(Product.class)) {
			mongoTemplate.createCollection(Product.class);
		}
		
		// If no price was set, it's free! yay!
		if (product.getPrice() == null) {
			product.setPrice(0);
		}
		
		mongoTemplate.save(product);
	}
	
	/**
	 * Gets a product document for the given name
	 * @param product - product's name
	 * @return - product document
	 */
	public Product findProductByName(String product) {
		Query query = new Query(Criteria.where("name").is(product));
		return mongoTemplate.findOne(query, Product.class);
	}
}
