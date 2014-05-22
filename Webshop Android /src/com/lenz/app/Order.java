package com.lenz.app;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class Order extends StartPage {
		@Override
		protected void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			setContentView(R.layout.list_all_orders_layout);
			startService(new Intent(this, BackgroundService.class));
			
			new GetOrders().execute();	
		}
		
		class GetOrders extends AsyncTask<Void, Void, JSONArray>{
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
						new HttpGet("http://" + ip + ":9000/api/orders"),   
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
				ListView listView = (ListView) findViewById(R.id.list_view_orders);
				listView.setAdapter(new OrdersAdapter(result));
			}
		}
		
		class OrdersAdapter extends BaseAdapter {
			private JSONArray orders;

			public OrdersAdapter(JSONArray orders) {
				this.orders = orders;
			}

			@Override
			public int getCount() {
				return orders.length();
			}

			@Override
			public Object getItem(int index) {
				try {
					return orders.getJSONObject(index);
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
				
				View orderListItem = getLayoutInflater().inflate(R.layout.order_list_item, parent, false);
							
				TextView orderProduct = (TextView) orderListItem
						.findViewById(R.id.order_product);
				
				TextView orderQuantity = (TextView) orderListItem
						.findViewById(R.id.order_quantity);
				
				TextView orderCost = (TextView) orderListItem
						.findViewById(R.id.order_cost);
				
				TextView orderUser = (TextView) orderListItem
						.findViewById(R.id.order_user);
				
				try {
						
					JSONObject order = orders.getJSONObject(index);		
					orderProduct.setText(order.getString("productName"));
					orderQuantity.setText("Quantity:" + " " + (order.getString("quantity")));
					orderCost.setText("Total Cost:" + " " + (order.getString("cost")));
					orderUser.setText(order.getJSONObject("user").getString("firstName"));

				} catch (JSONException e){
					throw new RuntimeException(e);
				}
			
				return orderListItem;	
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
			
			if(item.getItemId() == R.id.list_all_cart){
				startActivity(new Intent(this, ShoppingBasket.class));
				
				return true;
			}
			return false;
		}
	}