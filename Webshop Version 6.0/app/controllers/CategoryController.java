package controllers;


import java.util.List;
import java.util.Map;

import play.db.jpa.JPA;
import play.db.jpa.Transactional;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import views.html.*;
import models.Category;


public class CategoryController extends Controller {

	@Transactional
	public static Result newCategory() {
		Map<String, String[]> form = request().body().asFormUrlEncoded();
		
		String name = form.get("name")[0];
		String description = form.get("description")[0];
		
		Category  category = new Category();
		
		category.setCategoryName(name);
		category.setCategoryDescription(description);
		JPA.em().persist(category);
		
		return redirect(routes.CategoryController.showAllCat());
	}
	@Transactional
	@Security.Authenticated
	public static Result newCategoryForm(){
		List<Category> categories = JPA.em().createQuery("SELECT a FROM Category a", Category.class).getResultList();
		return ok(newCategory.render(categories));
	}
	
	@Transactional
	public static Result showCategory(int id) {
//		Category category = Ebean.find(Category.class, id);
		Category category = JPA.em().find(Category.class, id);
		return ok(showCategory.render(category));
	}
	
	@Transactional
	public static Result showAllCat(){
		List<Category> categories = JPA.em().createQuery("SELECT a FROM Category a", Category.class).getResultList();
		return ok(showAllCat.render(categories));
	}
}
