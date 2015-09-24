package controllers;

import java.util.List;
import java.util.Map;


import play.db.jpa.JPA;
import play.db.jpa.Transactional;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.*;
import models.User;

public class UserController extends Controller{
	@Transactional
	public static Result newUser() {
		Map<String, String[]> form = request().body().asFormUrlEncoded();
		
		String email = form.get("email")[0];
		String password = form.get("password")[0];
		String firstName = form.get("firstname")[0];
		String lastName = form.get("lastname")[0];
		String address = form.get("address")[0];
		String phoneNumber = form.get("phonenumber")[0];
		
		User user = new User();
		
		user.setEmail(email);
		user.setPassword(password);
		user.setFirstName(firstName);
		user.setLastName(lastName);
		user.setAddress(address);
		user.setPhoneNumber(phoneNumber);
//		
//		if(form.hasErrors(){
//			String errors="";
//			for(String propertyName: form.errors().keyset()){
//					List<ValidationError> errorForThisProperty = form.errors().get(propertyName);
//					ValidationError firstErrorForThisProperty = errorsForThisProperty.get(0);
//					
//					String errorMessage =  firstErrorForThisProperty.message();
//					
//					flash().put(propertyName, errorMessage);
//			}
//		} else {
//			
//		}
		
		JPA.em().persist(user);
		
		return redirect(routes.UserController.showNewUser());
	}
	@Transactional
	public static Result showNewUser(){
		List<User> users = JPA.em().createQuery("SELECT a FROM User a", User.class).getResultList();
		return ok(showNewUser.render(users));
	}
	@Transactional
	//@Security.Authenticated
	public static Result newUserForm(){
		return ok(newUser.render(null));
	}
	
	@Transactional
	public static Result showUser(int id) {
		User user = JPA.em().find(User.class, id);
		return ok(showUser.render(user));
	}
}
