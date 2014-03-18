package controllers;

import java.util.ArrayList;
import java.util.List;

import com.avaje.ebean.Ebean;

import models.Category;
import models.Product;
import models.ShoppingBasket;
import models.User;
import play.*;
import play.mvc.*;
import views.html.*;

public class Application extends Controller {
	
	public static Result createSomeData() {
		
		Product product = new Product(3, "LaFerrari", "2014 sport car", 1500000.0);
		Ebean.save(product);
		
		Product product2 = new Product(4, "Porsche 911", "2008 sport car", 750000.0);
		Ebean.save(product2);
		
		Product product3 = new Product(5, "Audi R8", "2010 sport car", 870000.0);
		Ebean.save(product3);
		
		Product product4 = new Product(6, "Ford Mustang GT", "2009 sport car", 540000.0);
		Ebean.save(product4);
		
		Category category = new Category(3, "Cars", "Porsche, Lamborghini, Audi");
		Ebean.save(category);
		
		Category category2 = new Category(4, "Fruits", "Apple, Bananas, Orange");
		Ebean.save(category2);
		
		Category category3 = new Category(5, "Computers", "Apple, Lenovo, Sony");
		Ebean.save(category3);
		
		User user = new User(2, "Lenz94", "Enzo", "Marcani", "Tyresö, Stockholm", "0720472240", "d4rkleo94_enzo@hotmail.com");
		Ebean.save(user);
		
		ShoppingBasket shoppingBasket = new ShoppingBasket(1, 10);
		Ebean.save(shoppingBasket);
		
		return ok("Data created");
	}

    public static Result index() {
    	
    	List<Product> products = Ebean.find(Product.class).findList();
    	List<Category> categories = Ebean.find(Category.class).findList();
    	List<User> users = Ebean.find(User.class).findList();
    	List<ShoppingBasket> shopping = Ebean.find(ShoppingBasket.class).findList();
        return ok(index.render(products, categories, users, shopping));
    }
    
    public static Result showProduct(int id) {
    	Product product = Ebean.find(Product.class, id);
    	
    	return ok(showProduct.render(product));
    }
    
    public static Result showCategory(int id) {
    	
    	Category category = Ebean.find(Category.class, id);
    	
    	return ok(showCategory.render(category));
    }
    
    public static Result showUser(int id) {
    	
    	User user = Ebean.find(User.class, id);
    	
    	return ok(showUser.render(user));
    }
    
    public static Result showShoppingBasket(int id) {
    	
    	ShoppingBasket shoppingBasket = Ebean.find(ShoppingBasket.class, id);
    	return ok(showShoppingBasket.render(shoppingBasket));
    }

}