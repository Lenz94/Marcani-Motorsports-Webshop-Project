package controllers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.TypedQuery;

import play.db.jpa.JPA;
import play.db.jpa.Transactional;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import views.html.*;
import models.Orders;
import models.Product;
import models.ShoppingBasket;
import models.User;

public class ShoppingBasketController extends Controller{

	@Transactional
	public static Result newShoppingBasket() {
		Map<String, String[]> form = request().body().asFormUrlEncoded();
		
		User userActive = MyAuthentication.getActiveUser().get(0);
		
		TypedQuery<ShoppingBasket> query = JPA.em().createQuery( "SELECT c FROM ShoppingBasket c WHERE c.user = :minParam1", models.ShoppingBasket.class);
		List<ShoppingBasket> activeCart = query.setParameter("minParam1", userActive).getResultList();
		
		Integer proquantity = Integer.parseInt(form.get("quantity")[0]);
		Integer productId = Integer.parseInt(form.get("product-id")[0]);
		
		Product product = JPA.em().find(Product.class, productId);
		

		ShoppingBasket shoppingBasket = new ShoppingBasket();
		
		shoppingBasket.setQuantity(proquantity);
		shoppingBasket.setProduct(product);
		shoppingBasket.setUser(userActive);

		

		JPA.em().persist(shoppingBasket);
	
		return redirect(routes.ShoppingBasketController.listAllCart());
	}

	@Transactional
    public static Result showShoppingBasket(int id) {
    	ShoppingBasket shoppingBasket = JPA.em().find(ShoppingBasket.class, id);
    	return ok(showShoppingBasket.render(shoppingBasket));
    }
	
	@Transactional
	@Security.Authenticated
		public static Result listAllCart(){
			User user = MyAuthentication.getActiveUser().get(0);
			return ok(listAllCart.render(user.shoppingbasket));

	}
	@Transactional
	public static Result order(){
		Map<String, String[]> form = request().body().asFormUrlEncoded();		
        User activeUser = MyAuthentication.getActiveUser().get(0);		
        List<ShoppingBasket> cartRecords = activeUser.shoppingbasket;
        
        if ((form.get("deleteall")) != null){
			String deletec = form.get("deleteall")[0];
			if (deletec.equals("yes")){
				for (ShoppingBasket cartRecord : cartRecords) {
					Orders order = new Orders();
					order.setUser(activeUser);
					order.setProductName(cartRecord.getProduct().getProductName());
					order.setQuantity(cartRecord.getQuantity());
					SimpleDateFormat sdf = new SimpleDateFormat("dd/M/yyyy");
					String dateString = sdf.format(new Date()); 		
					try {
						Date date = sdf.parse(dateString);
						order.setDateOrder(date);
					} catch (ParseException e) {			
						e.printStackTrace();
					}
					JPA.em().persist(order);
					JPA.em().remove(cartRecord);
				}
			}
		}
        return ok(order.render());
	}
	@Transactional
	public static Result changeCart(){
		Map<String, String[]> form = request().body().asFormUrlEncoded();
		int id = Integer.parseInt(form.get("delete")[0]);
		ShoppingBasket p = JPA.em().find(ShoppingBasket.class, id);
		 if (p != null){
			 JPA.em().remove(p);
		 }	
		 return redirect(routes.ShoppingBasketController.listAllCart());
	}
}
