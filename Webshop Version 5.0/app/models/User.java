package models;

import java.util.List;
import play.data.validation.Constraints.*;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class User {
	
	@Id
	@GeneratedValue
	private int userId;
	@Required
	@Email
	private String email;
	
	@Required
	private String password;
	
	@Required
	private String firstName;
	
	@Required
	private String lastName;
	private String address;
	private String phoneNumber;
	
	
	
	@OneToMany(mappedBy = "user")
	public List<ShoppingBasket> shoppingbasket;
	
	@Override
	public String toString(){
		return firstName;
	}
	
	public User() {
		
	}	
	
	public User(String email, String password, String firstName, String lastName, String address, String phoneNumber) {
		this.email = email;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.address = address;
		this.phoneNumber = phoneNumber;
		
	}

	public int getUserId() {
		return userId;
	}
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	

	
}
