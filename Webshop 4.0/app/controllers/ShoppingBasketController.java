package controllers;

import play.db.jpa.JPA;
import play.db.jpa.Transactional;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.*;

import models.ShoppingBasket;

public class ShoppingBasketController extends Controller{
	@Transactional
	public static Result newShoppingBasket() {
		ShoppingBasket shoppingBasket = new ShoppingBasket(10);
		JPA.em().persist(shoppingBasket);
	
		return redirect(routes.DefaultController.index());
	}
	@Transactional
    public static Result showShoppingBasket(int id) {
    	ShoppingBasket shoppingBasket = JPA.em().find(ShoppingBasket.class, id);
    	return ok(showShoppingBasket.render(shoppingBasket));
    }
}
