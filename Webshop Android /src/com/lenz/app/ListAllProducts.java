package com.lenz.app;

import java.util.ArrayList;
import java.util.List;

import com.lenz.app.R;

import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


public class ListAllProducts extends StartPage {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.list_all_products_layout);
		startService(new Intent(this, BackgroundService.class));
		
		new GetProducts().execute();
		}
		
    class AddCart extends AsyncTask<Void, Void, Boolean>{
    	private String ip;
    	private String proquantity;
    	private String productId;

		public String getProquantity() {
			return proquantity;
		}
		public void setProquantity(String proquantity) {
			this.proquantity = proquantity;
		}
		public String getProductId() {
			return productId;
		}
		public void setProductId(String productId) {
			this.productId = productId;
		}
		
		public AddCart(String ip){
    		this.ip = ip;
    	}
    	@Override
    	protected Boolean doInBackground(Void... params) {
    		try{
    			HttpPut put = new HttpPut("http://" + ip + ":9000/new-shoppingbasket-mobile");
    			DefaultHttpClient client = new DefaultHttpClient();
    			
    			if (cookies != null) {
					for (Cookie cookie : cookies) {
						client.getCookieStore().addCookie(cookie);
					}
				}
    			
    			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
    			nameValuePairs.add(new BasicNameValuePair("quantity", proquantity));
    			nameValuePairs.add(new BasicNameValuePair("product-id", productId));
    			
    			put.setEntity(new UrlEncodedFormEntity(nameValuePairs));
    			
    			
    			client.execute(put, new BasicResponseHandler());
    			
    
    			cookies = client.getCookieStore().getCookies();
    			
    			return true;
    	} catch(Exception e){
    		Log.e("Error adding product", e.getMessage());
    		return false;
    	}	
    }
    	@Override
    	protected void onPostExecute(Boolean success) {		
    		if(success == true){
    			Toast.makeText(getApplicationContext(), "Success!",
    			Toast.LENGTH_LONG).show();
    			Intent intent = new Intent(getApplicationContext(), ShoppingBasket.class);
    			startActivity(intent);
    			overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
    		} else{
    			Toast.makeText(getApplicationContext(), "Failed",
    			Toast.LENGTH_LONG).show();
    		}
    		
    	}
   }
	
	
	class GetProducts extends AsyncTask<Void, Void, JSONArray>{
		@Override
		protected JSONArray doInBackground(Void... params) {
			try {
				String myResponse = new DefaultHttpClient()
					.execute(
					new HttpGet("http://" + ip + ":9000/api/products"),   
					new BasicResponseHandler()
					);
				
				return new JSONArray(myResponse);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
		
		@Override
		protected void onPostExecute(JSONArray result) {
			ListView listView = (ListView) findViewById(R.id.list_view_products);
			listView.setAdapter(new ProductAdapter(result));
			
		}
	}
	
	class ProductAdapter extends BaseAdapter {
		private JSONArray products;

		public ProductAdapter(JSONArray products) {
			this.products = products;
		}

		@Override
		public int getCount() {
			return products.length();
		}

		@Override
		public Object getItem(int index) {
			try {
				return products.getJSONObject(index);
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
			
			View productListItem = getLayoutInflater().inflate(R.layout.products_list_item, parent, false);
						
			TextView productName = (TextView) productListItem
					.findViewById(R.id.product_name);
			
			TextView productDescription = (TextView) productListItem
					.findViewById(R.id.product_description);
			
			TextView productCost = (TextView) productListItem
					.findViewById(R.id.product_cost);
			
			TextView productCategory = (TextView) productListItem
					.findViewById(R.id.product_category);
			
			final TextView productId = (TextView) productListItem
					.findViewById(R.id.product_id);
			
			Button button = (Button) productListItem
					.findViewById(R.id.add);
			
				
			try {
					
				final JSONObject product = products.getJSONObject(index);
			
				productName.setText(product.getString("productName"));
				productDescription.setText(product.getString("description"));
				productCost.setText((product.getString("cost")+" "+"kr"));
				productCategory.setText(product.getJSONObject("category").getString("categoryName"));
				productId.setText(product.getString("productId"));
				productId.setVisibility(View.GONE);
				
		    	button.setOnClickListener(new View.OnClickListener() {
					
					@Override
					public void onClick(View view) {
						AddCart addcart = new AddCart(ip);
						
						//TextView productId = (TextView) findViewById(R.id.product_id);
						
						addcart.setProductId(productId.getText().toString());
						Spinner numbers = (Spinner) findViewById(R.id.numbers);
						
						addcart.setProquantity(String.valueOf(numbers.getSelectedItem()));
						
						addcart.execute();
						
						}
					});
			
			} catch (JSONException e){
				throw new RuntimeException(e);
			}
		
			return productListItem;	
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


