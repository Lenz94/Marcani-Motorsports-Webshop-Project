package models;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Product {
	@Id
	private int id;
	private String name;
	private String description;
	private double cost;
	
	public Product(int id, String name, String description, double cost) {
		
		this.id = id;
		this.name = name;
		this.description = description;
		this.cost = cost;
	}
	
	public int getId() {
		return id;
	}
	
	
	public String getName() {
		return name;
	}
	
	public String getDescription() {
		return description;
	}
	
	public double getCost() {
		return cost;
	}
		

}
