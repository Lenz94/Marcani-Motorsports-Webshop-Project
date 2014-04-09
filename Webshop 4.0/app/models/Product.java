package models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "Product")
public class Product {
	@Id
	@GeneratedValue
	private int productId;
	public String productName;
	private String description;
	private double cost;
	
	@ManyToOne
	public Category category;
	
	public Product() {
		
	}
	
	public Product(String productName, String description, double cost) {
		this.productName = productName;
		this.description = description;
		this.cost = cost;
	}
	
	public int getProductId() {
		return productId;
	}
	
	
	public String getProductName() {
		return productName;
	}
	
	public String getDescription() {
		return description;
	}
	
	public double getCost() {
		return cost;
	}
		

}
