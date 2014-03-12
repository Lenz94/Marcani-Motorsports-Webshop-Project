package controllers;

import java.util.Arrays;
import java.util.List;

import play.*;
import play.mvc.*;
import views.html.*;

public class Product extends Controller {
	public static Result listAllProducts(){
		List<models.Product> products = askDatabaseForAllProducts();
		
		return ok(listallproducts.render(products));
	}
	
	public static Result showOneProduct(int id){
		models.Product product = askDatabaseForOneProduct(id);
		
		if(product == null){
			return notFound("We are sorry but we couldn't find this vehicle");
		}
		
		return ok(showoneproduct.render(product));
	}
	
	private static List<models.Product> askDatabaseForAllProducts(){
		return Arrays.asList(new models.Product[]{
			new models.Product(1, "LaFerrari", 2345000),
			new models.Product(2, "Porsche GTR 2", 158000),
			new models.Product(3, "Aston Martin S8", 2703000),
			new models.Product(4, "Lamborghini Gallardo", 143000),
			new models.Product(5, "Audi S8", 2345000),
			new models.Product(6, "Mercedez Benz GT", 158000)
		});
	}
	
	private static models.Product askDatabaseForOneProduct(int id) {
		switch(id){
			case 1:
			return new models.Product(1, "LaFerrari", 2345000);
			
			case 2:
			return new models.Product(2, "Porsche GTR 2", 1580000);
			
			case 3:
			return new models.Product(3, "Aston Martin S8", 2703000);
			
			case 4:
			return new models.Product(4, "Lamborghini Gallardo", 143000);	
			
			case 5:
			return new models.Product(5, "Audi S8", 2345000);
			
			case 6:
			return new models.Product(6, "Mercedez Benz GT", 158000);
				
			default:
			return null;
		}
	}
}
