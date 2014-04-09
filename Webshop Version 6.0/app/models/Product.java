package models;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "Product")
public class Product {
	@Id
	@GeneratedValue
	private int productId;
	private String productName;
	private String description;
	private double cost;
	
	@ManyToOne
	public Category category;
	
	@OneToMany(mappedBy = "product")
	public List<ShoppingBasket> shoppingbasket;
	
	@Override
	public String toString(){
		return productName;
	}
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
	public void setProductName(String productName) {
		this.productName = productName;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setCost(double cost) {
		this.cost = cost;
	}

}
