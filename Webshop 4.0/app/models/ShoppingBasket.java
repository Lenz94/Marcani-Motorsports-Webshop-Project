package models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;


@Entity
public class ShoppingBasket {
	
	@Id
	@GeneratedValue
	private int shoppingBaskedId;
	private int quantity;
	
	public ShoppingBasket(){
		
	}
	
	public ShoppingBasket(int quantity) {
		this.quantity = quantity;	
		
	}

	public int getShoppingBaskedId() {
		return shoppingBaskedId;
	}

	public void setId(int shoppingBaskedId) {
		this.shoppingBaskedId = shoppingBaskedId;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}


}
