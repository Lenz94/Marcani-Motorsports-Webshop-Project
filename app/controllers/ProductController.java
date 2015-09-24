package controllers;

import play.db.jpa.JPA;
import play.db.jpa.Transactional;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;

import views.html.*;


import java.util.Map;
import java.util.List;

import models.Category;
import models.Product;



public class ProductController extends Controller {
	
	@Transactional
	public static Result newProduct(){
		Map<String, String[]> form = request().body().asFormUrlEncoded();
		
		String name = form.get("name")[0];
		String description = form.get("description")[0];
		double cost = Double.parseDouble(form.get("cost")[0]);
		Integer categoryId = Integer.parseInt(form.get("category-id")[0]);
		
		Category category = JPA.em().find(Category.class, categoryId);
		
		Product  product = new Product();
		product.setProductName(name);
		product.setDescription(description);
		product.setCost(cost);
		product.category = category;
		JPA.em().persist(product);
		
		return redirect(routes.ProductController.showAllPro());
	}
	@Transactional
	@Security.Authenticated
	public static Result newProductForm(){
		List<Category> categories = JPA.em().createQuery("SELECT a from Category a", Category.class).getResultList();
		return ok(newProduct.render(categories));
	}
	@Transactional
	public static Result showAllPro(){
		List<Product> products = JPA.em().createQuery("SELECT a FROM Product a", Product.class).getResultList();
		return ok(showAllPro.render(products));
	}
	@Transactional
	public static Result showProduct(int id) {
		Product product = JPA.em().find(Product.class, id);
		return ok(showProduct.render(product));
	}
}