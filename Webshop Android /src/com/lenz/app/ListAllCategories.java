package com.lenz.app;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.lenz.app.R;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;



public class ListAllCategories extends StartPage {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.list_all_categories_layout);
		startService(new Intent(this, BackgroundService.class));
		
		new GetCategories().execute();	
		
//		Button button = (Button) findViewById(R.id.like);
//    	
//    	button.setOnClickListener(new View.OnClickListener() {
//			
//			@Override
//			public void onClick(View view) {
//				Toast.makeText(getApplicationContext(), "Oh!, Than You For The Like",
//		    			Toast.LENGTH_LONG).show();
//			}
//		});
	}
	
	class GetCategories extends AsyncTask<Void, Void, JSONArray>{
		@Override
		protected JSONArray doInBackground(Void... params) {
			try {
				String myResponse = new DefaultHttpClient()
					.execute(
					new HttpGet("http://" + ip + ":9000/api/categories"),   
					new BasicResponseHandler()
					);
				
				return new JSONArray(myResponse);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
		
		@Override
		protected void onPostExecute(JSONArray result) {
			ListView listView = (ListView) findViewById(R.id.list_view_categories);
			listView.setAdapter(new CategoryAdapter(result));
		}
	}
	
	class CategoryAdapter extends BaseAdapter {
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
			return index;
		}

		@Override
		public View getView(int index, View convertView, ViewGroup parent) {
			
			View categoryListItem = getLayoutInflater().inflate(R.layout.categories_list_item, parent, false);
						
			TextView categoryName = (TextView) categoryListItem
					.findViewById(R.id.category_name);
			
			TextView categoryDescription = (TextView) categoryListItem
					.findViewById(R.id.category_description);
			
			ImageView imageview2 = (ImageView) categoryListItem
					.findViewById(R.id.thumbnail);
			
			try {
					
				JSONObject category = categories.getJSONObject(index);		
				categoryName.setText(category.getString("categoryName"));
				categoryDescription.setText(category.getString("categoryDescription"));
				
				
//				Uri imageUri = Uri.parse(ListAllCategories.this.getIntent().getExtras().getString("imageUri"));
				
//				Uri imageUri = getIntent().getData();
//				imageview2.setImageURI(imageUri);
				
				String path = getIntent().getStringExtra("imagePath");
			    Drawable image = Drawable.createFromPath(path);
			    imageview2.setImageDrawable(image);

			} catch (JSONException e){
				throw new RuntimeException(e);
			}
		
			return categoryListItem;	
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
		
		if(item.getItemId() == R.id.list_all_orders){
			startActivity(new Intent(this, Order.class));
			
			return true;
		}
		return false;
	}
}
