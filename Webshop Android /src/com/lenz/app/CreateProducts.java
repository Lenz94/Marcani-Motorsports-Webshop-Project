package com.lenz.app;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.lenz.app.R;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class CreateProducts extends StartPage {
    @Override
    protected void onCreate(Bundle savedInstanceState){
    	super.onCreate(savedInstanceState);
    	
    	setContentView(R.layout.create_products_layout);
    	startService(new Intent(this, BackgroundService.class));
    	
    	new GetCategoriesFromServer().execute();
    	
    	Button button = (Button) findViewById(R.id.create_products);
    	
    	button.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View view) {
				CreateProductOnServer createProductOnServer = new CreateProductOnServer(ip);
			
				EditText name = (EditText) findViewById(R.id.product_name);
				EditText description = (EditText) findViewById(R.id.product_description);
				EditText cost = (EditText) findViewById(R.id.product_cost);
				Spinner category = (Spinner) findViewById(R.id.list_categories);
				
				if(name.getText().toString().equals("")){
                    name.setError("Name must be filled");
                }

                if(description.getText().toString().equals("")){
                    description.setError("Description must be filled");
                }
                
                if(cost.getText().toString().equals("")){
                    cost.setError("Cost must be filled");
                }
                
                if(name.getText().toString().equals("")&&(description.getText().toString().equals("")&&(cost.getText().toString().equals("")))){
                    name.setError("Email must filled");
                    description.setError("Password must be filled");
                    cost.setError("Cost must be filled");
                }
				
				String categoryId = Integer.toString((int) category.getSelectedItemId());
				
				Toast.makeText(getApplicationContext(), "category: " + category.getSelectedItemId(), Toast.LENGTH_LONG).show();
				
				if(name.getText().toString().length()==0 || description.getText().toString().length()==0 || cost.getText().toString().length()==0 ){
					Toast.makeText(getApplicationContext(), "No product created ", 
					Toast.LENGTH_LONG).show();
					}else{
						createProductOnServer.setProductName(name.getText().toString());
						createProductOnServer.setDescription(description.getText().toString());
						createProductOnServer.setCost(cost.getText().toString());
						createProductOnServer.setCategory(categoryId);
						
						createProductOnServer.execute();
					}
				}
			});
		}
		
    class CreateProductOnServer extends AsyncTask<Void, Void, Boolean>{
    	private String ip;
    	private String name;
    	private String description;
    	private String cost;
    	private String category;
    	
    	public String getCategory() {
			return category;
		}
		public void setCategory(String category) {
			this.category = category;
		}
		
		public String getProductName() {
			return name;
		}
		public void setProductName(String name) {
			this.name = name;
		}
		public String getDescription() {
			return description;
		}
		public void setDescription(String description) {
			this.description = description;
		}
		public String getCost() {
			return cost;
		}
		public void setCost(String cost) {
			this.cost = cost;
		}
		public CreateProductOnServer(String ip){
    		this.ip = ip;
    	}
    	@Override
    	protected Boolean doInBackground(Void... params) {
    		try{
    			HttpPut put = new HttpPut("http://" + ip + ":9000/new-product-mobile");
    			
    			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
    			nameValuePairs.add(new BasicNameValuePair("name", name));
    			nameValuePairs.add(new BasicNameValuePair("description", description));
    			nameValuePairs.add(new BasicNameValuePair("cost", cost));
    			nameValuePairs.add(new BasicNameValuePair("category-id", category));
    			put.setEntity(new UrlEncodedFormEntity(nameValuePairs));
    		
    			new DefaultHttpClient().execute(put, new BasicResponseHandler());
    			return true;
    	} catch(Exception e){
    		Log.e("Error creating product", e.getMessage());
    		return false;
    	}	
    }
    	@Override
    	protected void onPostExecute(Boolean success) {		
    		if(success == true){
    			Toast.makeText(getApplicationContext(), "Success!",
    			Toast.LENGTH_LONG).show();
    			Intent intent = new Intent(getApplicationContext(), CreateProducts.class);
    			startActivity(intent);
    		} else{
    			Toast.makeText(getApplicationContext(), "Failed",
    			Toast.LENGTH_LONG).show();
    		}
    		
    	}
    }
    class GetCategoriesFromServer extends AsyncTask<Void, Void, JSONArray> {
		@Override
		protected JSONArray doInBackground(Void... params) {
			try {
				String response = new DefaultHttpClient().execute(new HttpGet(
						"http://" + ip + ":9000/api/categories"),
						new BasicResponseHandler());
				return new JSONArray(response);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
		
		@Override
		protected void onPostExecute(JSONArray result) {
			Spinner spinner = (Spinner) findViewById(R.id.list_categories);
			spinner.setAdapter(new CategoryAdapter(result));
		}
	}
	
	class CategoryAdapter extends BaseAdapter implements SpinnerAdapter {
		private JSONArray categories;

		public CategoryAdapter(JSONArray categories) {
			this.categories = categories;
		}

		@Override
		public int getCount() {
			return categories.length();
		}

		@Override
		public Object getItem(int index) {
			try {
				return categories.getJSONObject(index);
			} catch (JSONException e) {
				throw new RuntimeException(e);
			}
		}

		@Override
		public long getItemId(int index) {
			try {
				return categories.getJSONObject(index).getInt("categoryId");
			} catch (JSONException e) {
				throw new RuntimeException(e);
			}
		}

		@Override
		public View getView(int index, View convertView, ViewGroup parent) {
			TextView textView = new TextView(getApplicationContext());
			textView.setBackgroundResource(R.drawable.edittext_rounded_corners);
			
			try {
				JSONObject category = categories.getJSONObject(index);
				textView.setText(category.getString("categoryName"));
			} catch (JSONException e) {
				throw new RuntimeException(e);
			}
			textView.setTextSize(18);
			return textView;
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
