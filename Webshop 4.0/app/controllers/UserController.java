package controllers;

import play.db.jpa.JPA;
import play.db.jpa.Transactional;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.*;

import models.User;

public class UserController extends Controller{
	@Transactional
	public static Result newUser() {
		User user1 = new User(2, "Lenz94", "Enzo", "Marcani", "Tyresö, Stockholm", "0720472240", "d4rkleo94_enzo@hotmail.com");
		JPA.em().persist(user1);
		return redirect(routes.DefaultController.index());
	}
	@Transactional
	public static Result showUser(int id) {
		User user = JPA.em().find(User.class, id);
		return ok(showUser.render(user));
	}
}
