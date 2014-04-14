package models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import play.data.validation.Constraints.Email;
import play.data.validation.Constraints.MaxLength;
import play.data.validation.Constraints.MinLength;
import play.data.validation.Constraints.Required;

@Entity
public class Student {
	@Id
	@GeneratedValue
	public int id;
	
	@Required
	@MinLength(2)
	@MaxLength(20)
	public String name;
	
	@Required
	@MinLength(2)
	@MaxLength(20)
	public String lastname;
	
	@Required
	@Email
	@MinLength(5)
	@MaxLength(40)
	public String email;
	
	@Required
	@MinLength(9)
	@MaxLength(11)
	public String phoneNumber;
}
