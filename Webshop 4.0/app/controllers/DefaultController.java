package controllers;


import play.db.jpa.JPA;
import play.db.jpa.Transactional;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.*;

import java.util.List;

import models.Category;
import models.Product;
import models.ShoppingBasket;
import models.User;

public class DefaultController extends Controller {
	
	@Transactional
    public static Result index() {
		List<Product> products = JPA.em().createQuery("SELECT a FROM Product a", Product.class).getResultList();
		List<Category> categories = JPA.em().createQuery("SELECT p FROM Category p", Category.class).getResultList();
		List<User> users = JPA.em().createQuery("SELECT c FROM User c", User.class).getResultList();
		List<ShoppingBasket> shopping = JPA.em().createQuery("SELECT d FROM ShoppingBasket d", ShoppingBasket.class).getResultList();
		
        return ok(index.render(products, categories, users, shopping));
    }
}