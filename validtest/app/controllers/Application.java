package controllers;

import java.util.List;


import models.User;

import com.avaje.ebean.Ebean;

import play.*;
import play.data.Form;
import play.libs.Json;
import play.mvc.*;
import views.html.*;

public class Application extends Controller {

    public static Result index() {
        return ok(index.render("Your new application is ready."));
    }
    
    public static Result getUser() {
    	List<User> users = Ebean.find(User.class).findList();
        return ok(Json.toJson(users));
    }
    public static Result getOneUser(int id) {
     User user = Ebean.find(models.User.class, id);	
        return ok(Json.toJson(user));
    }
    
   
    public static Result createUser() {
    	User user = Form.form(User.class).bindFromRequest().get();	
    	Ebean.save(user);  	
        return ok();
    }
    
    public static Result uppdateUser(int id) {
    	User user = Ebean.find(User.class, id);
    	User form = Form.form(User.class).bindFromRequest().get();
    	user.setFirstName(form.getFirstName());
    	user.setLastName(form.getLastName());
    	user.setPhoneNumber(form.getPhoneNumber());
    	Ebean.update(user);
        return ok();
       }    
    
    public static Result delete(int id) {
        User user = Ebean.find(models.User.class, id);	
        Ebean.delete(user);
        return ok();
       }     

}