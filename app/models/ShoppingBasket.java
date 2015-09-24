package models;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;


@Entity
public class ShoppingBasket {
	
	@Id
	@GeneratedValue
	private int shoppingBaskedId;
	private int quantity;

	
	@ManyToOne
	@JoinColumn(name="user")
	public User user;
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	@ManyToOne
	@JoinColumn(name="product")
	public Product product;
	
	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}	
	
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
