package com.lenz.app;


import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ShoppingBasket extends StartPage {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.list_all_cart_layout);
		startService(new Intent(this, BackgroundService.class));
		
		new GetCart().execute();	
		
		Button button = (Button) findViewById(R.id.order);
		
		button.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View view) {
				Order order = new Order(ip);
				order.execute();
				}
			});
	}
	
	class GetCart extends AsyncTask<Void, Void, JSONArray>{
		@Override
		protected JSONArray doInBackground(Void... params) {
			try {
				DefaultHttpClient client = new DefaultHttpClient();

				if (cookies != null) {
					for (Cookie cookie : cookies) {
						client.getCookieStore().addCookie(cookie);
					}
				}
				String myResponse = client.execute(
					new HttpGet("http://" + ip + ":9000/api/cart"),   
					new BasicResponseHandler()
					);
				
				cookies = client.getCookieStore().getCookies();
				
				return new JSONArray(myResponse);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
		
		@Override
		protected void onPostExecute(JSONArray result) {
			ListView listView = (ListView) findViewById(R.id.list_view_cart);
			listView.setAdapter(new CartAdapter(result));
		}
	}
	
	class CartAdapter extends BaseAdapter {
		private JSONArray shoppingbasket;

		public CartAdapter(JSONArray shoppingbasket) {
			this.shoppingbasket = shoppingbasket;
		}

		@Override
		public int getCount() {
			return shoppingbasket.length();
		}

		@Override
		public Object getItem(int index) {
			try {
				return shoppingbasket.getJSONObject(index);
			} catch (JSONException e) {
				throw new RuntimeException(e);
			}
		}

		@Override
		public long getItemId(int index) {
			return index;
		}

		@Override
		public View getView(int index, View convertView, ViewGroup parent) {
			
			View cartListItem = getLayoutInflater().inflate(R.layout.cart_list_item, parent, false);
			
			TextView cartProduct = (TextView) cartListItem
					.findViewById(R.id.cart_product);
			
			TextView cartQuantity = (TextView)  cartListItem
					.findViewById(R.id.cart_quantity);
			
			TextView cartCost = (TextView) cartListItem
					.findViewById(R.id.cart_cost);
			
			TextView cartUser = (TextView) cartListItem
					.findViewById(R.id.cart_user);
			
			try {
					
				JSONObject shoppingBasket = shoppingbasket.getJSONObject(index);
				cartProduct.setText(shoppingBasket.getJSONObject("product").getString("productName"));
				cartQuantity.setText("Quantity:" + " " + (shoppingBasket.getString("quantity")));
				cartCost.setText(("Total Price: " + shoppingBasket.getString("totalCost") + " kr"));
				cartUser.setText(shoppingBasket.getString("customer"));
				
				

			} catch (JSONException e){
				throw new RuntimeException(e);
			}
		
			return cartListItem;	
		}
		
	}
	
	class Order extends AsyncTask<Void, Void, Boolean>{
    	private String ip;
    	
		public Order(String ip){
    		this.ip = ip;
    	}
    	@Override
    	protected Boolean doInBackground(Void... params) {
    		try{
    			HttpPost post = new HttpPost("http://" + ip + ":9000/order-mobile");
    			DefaultHttpClient client = new DefaultHttpClient();
    			
    			if (cookies != null) {
					for (Cookie cookie : cookies) {
						client.getCookieStore().addCookie(cookie);
					}
				}	    			
    			client.execute(post);
    			cookies = client.getCookieStore().getCookies();
    			
    			return true;
    	} catch(Exception e){
    		Log.e("Error ordering", e.getMessage());
    		return false;
    	}	
    }
    	@Override
    	protected void onPostExecute(Boolean success) {		
    		if(success == true){
    			Toast.makeText(getApplicationContext(), "Success!",
    			Toast.LENGTH_LONG).show();
    			
    			Intent intent = new Intent(getApplicationContext(), StartPage.class);
    			startActivity(intent);
    			overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
    			
    		} else{
    			Toast.makeText(getApplicationContext(), "Failed",
    			Toast.LENGTH_LONG).show();
    		}
    		
    	}
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
		
		if(item.getItemId() == R.id.order){
			startActivity(new Intent(this, Order.class));
			
			return true;
		}
		
		if(item.getItemId() == R.id.list_all_orders){
			startActivity(new Intent(this, Order.class));
			
			return true;
		}
		
		return false;
	}
}
