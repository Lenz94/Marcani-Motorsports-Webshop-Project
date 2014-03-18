package models;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class ShoppingBasket {
	
	@Id
	private int id;
	private int quantity;
	
	public ShoppingBasket(int id, int quantity) {
		
		this.id = id;
		this.quantity = quantity;	
		
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}


}
