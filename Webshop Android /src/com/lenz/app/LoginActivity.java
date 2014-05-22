package com.lenz.app;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import com.lenz.app.R;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends StartPage {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		//startService(new Intent(this, BackgroundService.class));
		
		Button button = (Button) findViewById(R.id.login);
    	
    	button.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View view) {
				LogInTask logInServer = new LogInTask(ip);
			
				EditText email = (EditText) findViewById(R.id.email);
				EditText passwd = (EditText) findViewById(R.id.passwd);
				
				if(email.getText().toString().equals("")){
                    email.setError("Dude, Your Email!");
                }

                if(passwd.getText().toString().equals("")){
                    passwd.setError("Seriously?");
                }

                if(email.getText().toString().equals("")&&(passwd.getText().toString().equals(""))){
                    email.setError("Dude, Your Email!");
                    passwd.setError("Seriously?");
                }
				
				logInServer.setEmail(email.getText().toString());
				logInServer.setPassword(passwd.getText().toString());
				
				logInServer.execute();
				}
			});
		}
		
	class LogInTask extends AsyncTask<Void, Void, Boolean>{
		private String ip;
    	private String email;
    	private String passwd;

		public String getEmail() {
			return email;
		}

		public void setEmail(String email) {
			this.email = email;
		}

		public String getPassword() {
			return passwd;
		}

		public void setPassword(String passwd) {
			this.passwd = passwd;
		}

		public LogInTask(String ip){
    		this.ip = ip;
    	}
		
	
		@Override
		protected Boolean doInBackground(Void... params) {
			try{
				HttpPost post = new HttpPost("http://" + ip + ":9000/logIn");
			
				List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
    			nameValuePairs.add(new BasicNameValuePair("email", email));
    			nameValuePairs.add(new BasicNameValuePair("passwd", passwd));
    			
    			post.setEntity(new UrlEncodedFormEntity(nameValuePairs));
    			
    			DefaultHttpClient client =  new DefaultHttpClient();
                client.execute(post, new BasicResponseHandler());
                cookies = client.getCookieStore().getCookies();

                for(Cookie cookie : cookies){
                    if(cookie.getName().equals("PLAY_FLASH")){
                        return false;
                    }
                }
                return true;
            }   catch(Exception e){
                throw new RuntimeException(e);

            }    

        }
		
		@Override
		protected void onPostExecute(Boolean success) {
			if(success == true){
    			Toast.makeText(getApplicationContext(), "You shall not pass!, nah just kidding",
    			Toast.LENGTH_LONG).show();
    			Intent intent = new Intent(getApplicationContext(), StartPage.class);
    			startActivity(intent);
    			
    			overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
    			
    		} else{
    			Toast.makeText(getApplicationContext(), "You shall not pass!",
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
