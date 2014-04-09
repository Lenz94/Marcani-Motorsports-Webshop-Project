package controllers;


import play.db.jpa.JPA;
import play.db.jpa.Transactional;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.*;


import models.Category;

public class CategoryController extends Controller {
	@Transactional
	public static Result newCategory() {
		Category category1 = new Category("Cars");
		JPA.em().persist(category1);
		
		Category category2 = new Category("Fruits");
		JPA.em().persist(category2);
		
		Category category3 = new Category("Computers");
		JPA.em().persist(category3);

		return redirect(routes.DefaultController.index());
	}
	
	@Transactional
	public static Result showCategory(int id) {
//		Category category = Ebean.find(Category.class, id);
		Category category = JPA.em().find(Category.class, id);
		return ok(showCategory.render(category));
	}
}
