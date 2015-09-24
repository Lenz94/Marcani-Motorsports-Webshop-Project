package controllers;

import java.util.List;
import java.util.Map;

import javax.persistence.TypedQuery;

import play.db.jpa.JPA;
import play.db.jpa.Transactional;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.*;

public class MyAuthentication extends Controller{
	@Transactional
	public static Result showLoginForm(){
		return ok(topmenu.render());
	}
	
	@Transactional
	public static Result logOut(){
		session().clear();
		return redirect(routes.DefaultController.index());
	}
	
	@Transactional
	public static Result loginUser(){
		Map<String, String[]> form = request().body().asFormUrlEncoded();

		String email = form.get("email")[0];
		String passwd = form.get("passwd")[0];
		
		TypedQuery<models.User> query = JPA.em().createQuery( "SELECT c FROM User c WHERE c.email = :minParam1 AND c.password = :minParam2", models.User.class);
		List<models.User> matchingUsers = query.setParameter("minParam1", email).setParameter("minParam2", passwd).getResultList();
		
		boolean usernameIsEmpty = "".equals(email);
		boolean passwordIsEmpty = "".equals(passwd);
		

		if(usernameIsEmpty || passwordIsEmpty){
			if(usernameIsEmpty){
				flash().put("username-empty", "yes");
			}
			if(passwordIsEmpty){
				flash().put("password-empty", "yes");
			}
			return redirect(routes.MyAuthentication.showLoginForm());
		}
				
		if(matchingUsers.size() == 1){
			session().put("username", email);
			//flash().put("username-login", "yes");
			return redirect(routes.DefaultController.index());
		} else {
			flash().put("no-username-password-match", "yes");
			return redirect(routes.MyAuthentication.showLoginForm());
		}

	}
	public static List<models.User> getActiveUser() {
		String email = session().get("username");
		TypedQuery<models.User> query = JPA.em().createQuery( "SELECT c FROM User c WHERE c.email = :minParam1", models.User.class);
		List<models.User> users = query.setParameter("minParam1", email).getResultList();
		return users;
	}

}
