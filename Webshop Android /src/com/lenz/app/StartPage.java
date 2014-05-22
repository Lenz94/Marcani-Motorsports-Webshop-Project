package com.lenz.app;

import java.util.List;

import org.apache.http.cookie.Cookie;

import com.lenz.app.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class StartPage extends Activity {
	static protected final String ip = "192.168.1.30";
	public static List<Cookie> cookies;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_menu);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main_menu, menu);
		menu.findItem(R.id.create_products).setVisible(false);
		menu.findItem(R.id.create_category).setVisible(false);
		menu.findItem(R.id.logout).setVisible(false);
		menu.findItem(R.id.list_all_cart).setVisible(false);
		menu.findItem(R.id.list_all_products).setVisible(false);
		menu.findItem(R.id.list_all_categories).setVisible(false);
		menu.findItem(R.id.list_all_orders).setVisible(false);
		
		//menu.findItem(R.layout.list_all_products_layout).getActionView().
		
		boolean loggedIn = false;
		if(cookies != null){
		for (Cookie cookie : cookies) {
			if ("PLAY_SESSION".equals(cookie.getName())) {
//				menu.findItem(R.id.loginmenu).setVisible(false);
				loggedIn = true;
				
			}
		}
		if (loggedIn) {
			
			startService(new Intent(this, BackgroundService.class));
			
			MenuItem item = menu.findItem(R.id.login);
			item.setVisible(false);
			menu.findItem(R.id.create_user).setVisible(false);			
			menu.findItem(R.id.create_products).setVisible(true);
			menu.findItem(R.id.create_category).setVisible(true);
			menu.findItem(R.id.list_all_cart).setVisible(true);
			menu.findItem(R.id.logout).setVisible(true);
			menu.findItem(R.id.list_all_products).setVisible(true);
			menu.findItem(R.id.list_all_categories).setVisible(true);
			menu.findItem(R.id.list_all_orders).setVisible(true);
			}
		}
		return true;
		
	}	
	
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		
		if(item.getItemId() == R.id.create_user){
			startActivity(new Intent(this, CreateUser.class));
			
			return true;
		}
		
		if(item.getItemId() == R.id.login){
			startActivity(new Intent(this, LoginActivity.class));
			
			return true;
		}
		
		if(item.getItemId() == R.id.logout){
			startActivity(new Intent(this, LogoutActivity.class));
			
			return true;
		}

		if(item.getItemId() == R.id.create_products){
			startActivity(new Intent(this, CreateProducts.class));
			
			return true;
		}
		
		if(item.getItemId() == R.id.list_all_products){
			startActivity(new Intent(this, ListAllProducts.class));
			
			return true;
		}
		
		if(item.getItemId() == R.id.create_category){
			startActivity(new Intent(this, CreateCategory.class));
			
			return true;
		}
		
		if(item.getItemId() == R.id.list_all_categories){
			startActivity(new Intent(this, ListAllCategories.class));
			
			return true;
		}
		if(item.getItemId() == R.id.list_all_cart){
			startActivity(new Intent(this, ShoppingBasket.class));
			
			return true;
		}
		
		if(item.getItemId() == R.id.list_all_orders){
			startActivity(new Intent(this, Order.class));
			
			return true;
		}
		return false;
	}
}
