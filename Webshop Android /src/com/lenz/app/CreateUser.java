package com.lenz.app;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class CreateUser extends StartPage {
	 @Override
	    protected void onCreate(Bundle savedInstanceState){ 	
	    	super.onCreate(savedInstanceState);
	    	
	    	setContentView(R.layout.create_user_layout);
	    	//startService(new Intent(this, BackgroundService.class));
	    	Button button = (Button) findViewById(R.id.create_user);
	    	
	    	button.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View view) {
					CreateUserOnServer createUserOnServer = new CreateUserOnServer(ip);
				
					EditText email = (EditText) findViewById(R.id.email);
					EditText password = (EditText) findViewById(R.id.password);
					EditText firstName = (EditText) findViewById(R.id.firstName);
					EditText lastName = (EditText) findViewById(R.id.lastName);
					EditText address = (EditText) findViewById(R.id.address);
					EditText phoneNumber = (EditText) findViewById(R.id.phoneNumber);
					
					if(email.getText().toString().equals("")){
	                    email.setError("Email must be filled");
	                }

	                if(password.getText().toString().equals("")){
	                    password.setError("Password must be filled");
	                }
	                
	                if(firstName.getText().toString().equals("")){
	                    firstName.setError("Firstname must be filled");
	                }
	                
	                if(lastName.getText().toString().equals("")){
	                    lastName.setError("Lastname must be filled");
	                }

	                if(address.getText().toString().equals("")){
	                	address.setError("Address must be filled");
	                }
	                
	                if(phoneNumber.getText().toString().equals("")){
	                    phoneNumber.setError("PhoneNumber must be filled");
	                }
	                
	                if(email.getText().toString().equals("")&&
	                		(password.getText().toString().equals("")&&
	                		(firstName.getText().toString().equals("")&&
	                		(lastName.getText().toString().equals("")&&
	    	                (address.getText().toString().equals("")&&
	    	    	        (phoneNumber.getText().toString().equals(""))))))){
	                	
	                    email.setError("Email must filled");
	                    password.setError("Password must be filled");
	                    firstName.setError("Firstname must be filled");
	                    lastName.setError("Lastname must be filled");
	                    address.setError("Address must be filled");
	                    phoneNumber.setError("PhoneNumber must be filled");
	                }
					
	                if(email.getText().toString().length()==0 ||
	                		password.getText().toString().length()==0 ||
	                		firstName.getText().toString().length()==0 || 
	                		lastName.getText().toString().length()==0 ||
	                		address.getText().toString().length()==0 ||
	                		phoneNumber.getText().toString().length()==0 ){
	                	
						Toast.makeText(getApplicationContext(), "No User created ", 
						Toast.LENGTH_LONG).show();
					}	else{
					createUserOnServer.setEmail(email.getText().toString());
					createUserOnServer.setPassword(password.getText().toString());
					createUserOnServer.setFirstName(firstName.getText().toString());
					createUserOnServer.setLastName(lastName.getText().toString());
					createUserOnServer.setAddress(address.getText().toString());
					createUserOnServer.setPhoneNumber(phoneNumber.getText().toString());
					createUserOnServer.execute();
						}
					}
				});
			}
			
	    class CreateUserOnServer extends AsyncTask<Void, Void, Boolean>{
	    	private String ip;
	    	private String email;
	    	private String password;
	    	private String firstName;
	    	private String lastName;
	    	private String address;
	    	private String phoneNumber;
	    	
	    	public String getEmail() {
				return email;
			}
			public void setEmail(String email) {
				this.email = email;
			}
			public String getPassword() {
				return password;
			}
			public void setPassword(String password) {
				this.password = password;
			}
			public String getFirstName() {
				return firstName;
			}
			public void setFirstName(String firstName) {
				this.firstName = firstName;
			}
			public String getLastName() {
				return lastName;
			}
			public void setLastName(String lastName) {
				this.lastName = lastName;
			}
			public String getAddress() {
				return address;
			}
			public void setAddress(String address) {
				this.address = address;
			}
			public String getPhoneNumber() {
				return phoneNumber;
			}
			public void setPhoneNumber(String phoneNumber) {
				this.phoneNumber = phoneNumber;
			}
	    	
			public CreateUserOnServer(String ip){
	    		this.ip = ip;
	    	}
	    	@Override
	    	protected Boolean doInBackground(Void... params) {
	    		try{
	    			HttpPut put = new HttpPut("http://" + ip + ":9000/new-user-mobile");
	    			
	    			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
	    			nameValuePairs.add(new BasicNameValuePair("email", email));
	    			nameValuePairs.add(new BasicNameValuePair("password", password));
	    			nameValuePairs.add(new BasicNameValuePair("firstname", firstName));
	    			nameValuePairs.add(new BasicNameValuePair("lastname", lastName));
	    			nameValuePairs.add(new BasicNameValuePair("address", address));
	    			nameValuePairs.add(new BasicNameValuePair("phonenumber", phoneNumber));
	    			put.setEntity(new UrlEncodedFormEntity(nameValuePairs));
	    		
	    			new DefaultHttpClient().execute(put, new BasicResponseHandler());
	    			return true;
	    	} catch(Exception e){
	    		Log.e("Error creating user", e.getMessage());
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
	    		} else{
	    			Toast.makeText(getApplicationContext(), "Failed",
	    			Toast.LENGTH_LONG).show();
	    		}
	    		
	    	}
	   }
	    @Override
		public boolean onOptionsItemSelected(MenuItem item) {
			
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