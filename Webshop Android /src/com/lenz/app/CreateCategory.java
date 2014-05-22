package com.lenz.app;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import com.lenz.app.R;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class CreateCategory extends StartPage {
	Uri imageUri;
	ImageView contactImageImgView;
	 @Override
	    protected void onCreate(Bundle savedInstanceState){ 	
	    	super.onCreate(savedInstanceState);
	    	
	    	setContentView(R.layout.create_categories_layout);
	    	
	    	startService(new Intent(this, BackgroundService.class));
	    	
	    	contactImageImgView = (ImageView) findViewById(R.id.imgViewContactImage);
			imageUri = Uri.parse("android.resource://com.lenz.app/drawable/car_logo.png");
			
			
			contactImageImgView.setOnClickListener(new View.OnClickListener() {
	            @Override
	            public void onClick(View v) {
	                Intent intent = new Intent();
	                intent.setType("image/*");
	                intent.setAction(Intent.ACTION_GET_CONTENT);
	                startActivityForResult(Intent.createChooser(intent, "Select Category Image"), 1);
	            }

	            });
	    	
	    	Button button = (Button) findViewById(R.id.create_category);
	    	
	    	button.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View view) {
					CreateCategoryOnServer createCategoryOnServer = new CreateCategoryOnServer(ip);
				
					EditText name = (EditText) findViewById(R.id.category_name);
					EditText description = (EditText) findViewById(R.id.category_description);
					
					if(name.getText().toString().equals("")){
	                    name.setError("Name must be filled");
	                }

	                if(description.getText().toString().equals("")){
	                    description.setError("Description must be filled");
	                }

	                if(name.getText().toString().equals("")&&(description.getText().toString().equals(""))){
	                    name.setError("Name must be filled");
	                    description.setError("Description must be filled");
	                }
					
	                if(name.getText().toString().length()==0 || description.getText().toString().length()==0){
						Toast.makeText(getApplicationContext(), "No category created ", 
						Toast.LENGTH_LONG).show();
						}	else{
					createCategoryOnServer.setCategoryName(name.getText().toString());
					createCategoryOnServer.setCategoryDescription(description.getText().toString());
					createCategoryOnServer.execute();
						}
					}
				});
			}
	 
	 public void onActivityResult(int reqCode, int resCode, Intent data) {
	        if (resCode == RESULT_OK) {
	            if (reqCode == 1) {
	            	imageUri = data.getData();
	                contactImageImgView.setImageURI(data.getData());
	                
	            }
	        }
	        
	    }
	 
			
	    class CreateCategoryOnServer extends AsyncTask<Void, Void, Boolean>{
	    	private String ip;
	    	private String name;
	    	private String description;

	    	
	    	public String getCategoryName() {
				return name;
			}
			public void setCategoryName(String name) {
				this.name = name;
			}
			public String getCategoryDescription() {
				return description;
			}
			public void setCategoryDescription(String description) {
				this.description = description;
			}
			
			public CreateCategoryOnServer(String ip){
	    		this.ip = ip;
	    	}
	    	@Override
	    	protected Boolean doInBackground(Void... params) {
	    		try{
	    			HttpPut put = new HttpPut("http://" + ip + ":9000/new-category-mobile");
	    			
	    			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
	    			nameValuePairs.add(new BasicNameValuePair("name", name));
	    			nameValuePairs.add(new BasicNameValuePair("description", description));
	    			put.setEntity(new UrlEncodedFormEntity(nameValuePairs));
	    			
	    			
	    		
	    			new DefaultHttpClient().execute(put, new BasicResponseHandler());
	    			
	    			
	    		    
	    			return true;
	    	} catch(Exception e){
	    		Log.e("Error creating category", e.getMessage());
	    		return false;
	    	}	
	    }
	    	@Override
	    	protected void onPostExecute(Boolean success) {		
	    		if(success == true){
	    			Toast.makeText(getApplicationContext(), "Success!",
	    			Toast.LENGTH_LONG).show();
	    			
	    			
	    			Intent sintent = new Intent(CreateCategory.this, ListAllCategories.class);
	    		    sintent.putExtra("imageUri", imageUri);
	    		    startActivity(sintent);
	    		    
	    			Intent intent = new Intent(getApplicationContext(), ListAllCategories.class);
	    			startActivity(intent);
	    			
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
