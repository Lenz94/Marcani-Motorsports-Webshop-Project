package models;


import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;


@Entity
public class Orders {
	@Id
	@GeneratedValue
	private int orderId;
	private int quantity;
	private String productName;
	private Date dateOrder;

	
	@ManyToOne
	@JoinColumn(name="user")
	public User user;
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	public Orders(){
		
	}
	public Orders(int orderId, Date dateOrder, int quantity){
		this.setOrderId(orderId);
		this.setDateOrder(dateOrder);	
		this.setQuantity(quantity);
	}
	
	public int getOrderId() {
		return orderId;
	}
	
	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public Date getDateOrder() {
		return dateOrder;
	}

	public void setDateOrder(Date dateOrder) {
		this.dateOrder = dateOrder;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public void setProductName(String productName) {
		this.productName = productName;
		
	}

	public String getProductName() {
		return productName;
	}

}
