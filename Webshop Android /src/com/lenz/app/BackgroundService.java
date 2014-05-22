package com.lenz.app;


import org.apache.http.client.methods.HttpGet;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;

import com.lenz.app.R;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.util.Log;

public class BackgroundService extends IntentService{
	private int numberOfCategories;
	private int numberOfProducts;
	private int numberOfUsers;
	private int numberOfOrders;
	String ip = "192.168.1.30";


	public BackgroundService() {
		super("BackgroundService");
	}

	@Override
	protected void onHandleIntent(Intent workIntent) {

		try {
			while (true) {
				
				new GetCategories().execute();
				new GetProducts().execute();
				new GetUser().execute();
				new GetOrders().execute();
				Thread.sleep(1000 * 5);
			}

		} catch (Exception e) {

			Log.e("Exception", e.getMessage());
		}

	}

	private void vibrateLight() {
		NotificationManager nManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		Notification n = new Notification();
		n.vibrate = new long[] { 100, 200, 100, 500 };
		n.defaults = 0;
		n.ledARGB = 0xFF00FFFF;
		n.flags = Notification.FLAG_SHOW_LIGHTS;
		n.ledOnMS = 1000;
		n.ledOffMS = 100;
		nManager.notify(0, n);
	}

	private void makeNotificationCategory() {
		

		NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(

		this).setSmallIcon(R.drawable.laferrari)

		.setContentTitle("Marcani Motorsports")

		.setContentText("New category of cars available, check it out!");

		int mNotificationId = (int) System.currentTimeMillis();
		
		Intent resultIntent = new Intent(this, ListAllCategories.class);
		TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
		stackBuilder.addParentStack(ListAllCategories.class);

		// Adds the Intent that starts the Activity to the top of the stack
		stackBuilder.addNextIntent(resultIntent);
		PendingIntent resultPendingIntent =
		        stackBuilder.getPendingIntent(
		            0,
		            PendingIntent.FLAG_UPDATE_CURRENT
		        );
		mBuilder.setContentIntent(resultPendingIntent);

		NotificationManager mNotifyMgr = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

		mNotifyMgr.notify(mNotificationId, mBuilder.build());
	}

	
	
	private void makeNotificationProduct() {
		NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(

		this).setSmallIcon(R.drawable.laferrari)

		.setContentTitle("Marcani Motorsports")

		.setContentText("New vehicle on sale, don't miss it!");

		int mNotificationId = (int) System.currentTimeMillis();
		
		Intent resultIntent = new Intent(this, ListAllProducts.class);
		TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
		stackBuilder.addParentStack(ListAllProducts.class);

		// Adds the Intent that starts the Activity to the top of the stack
		stackBuilder.addNextIntent(resultIntent);
		PendingIntent resultPendingIntent =
		        stackBuilder.getPendingIntent(
		            0,
		            PendingIntent.FLAG_UPDATE_CURRENT
		        );
		mBuilder.setContentIntent(resultPendingIntent);

		NotificationManager mNotifyMgr = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

		mNotifyMgr.notify(mNotificationId, mBuilder.build());
	}

	private void makeNotificationUser() {
		NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(

		this).setSmallIcon(R.drawable.laferrari)

		.setContentTitle("Marcani Motorsports")

		.setContentText("Welcome aboard!");

		int mNotificationId = (int) System.currentTimeMillis();
		
		Intent resultIntent = new Intent(this, CreateUser.class);
		TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
		stackBuilder.addParentStack(CreateUser.class);

		// Adds the Intent that starts the Activity to the top of the stack
		stackBuilder.addNextIntent(resultIntent);
		PendingIntent resultPendingIntent =
		        stackBuilder.getPendingIntent(
		            0,
		            PendingIntent.FLAG_UPDATE_CURRENT
		        );
		mBuilder.setContentIntent(resultPendingIntent);

		NotificationManager mNotifyMgr = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

		mNotifyMgr.notify(mNotificationId, mBuilder.build());
	}
	
	private void makeNotificationOrder() {
		NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(

		this).setSmallIcon(R.drawable.laferrari)

		.setContentTitle("Marcani Motorsports")

		.setContentText("Your order has been placed!");

		int mNotificationId = (int) System.currentTimeMillis();
		
		Intent resultIntent = new Intent(this, Order.class);
		TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
		stackBuilder.addParentStack(Order.class);

		// Adds the Intent that starts the Activity to the top of the stack
		stackBuilder.addNextIntent(resultIntent);
		PendingIntent resultPendingIntent =
		        stackBuilder.getPendingIntent(
		            0,
		            PendingIntent.FLAG_UPDATE_CURRENT
		        );
		mBuilder.setContentIntent(resultPendingIntent);

		NotificationManager mNotifyMgr = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

		mNotifyMgr.notify(mNotificationId, mBuilder.build());
	}
	class GetCategories extends AsyncTask<Void, Void, JSONArray> {

		@Override
		protected JSONArray doInBackground(Void... params) {
			try {
				String myResponse = new DefaultHttpClient().execute(
						new HttpGet("http://" + ip + ":9000/api/categories"),
						new BasicResponseHandler());

				return new JSONArray(myResponse);

			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}

		@Override
		protected void onPostExecute(JSONArray result) {

			if (result.length() > numberOfCategories) {
				if (numberOfCategories != 0) {
					makeNotificationCategory();
					vibrateLight();
					try {
						Uri notification = RingtoneManager
								.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
						Ringtone r = RingtoneManager.getRingtone(
								getApplicationContext(), notification);
						r.play();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				numberOfCategories = result.length();
			}
		}
	}

	class GetProducts extends AsyncTask<Void, Void, JSONArray> {

		@Override
		protected JSONArray doInBackground(Void... params) {
			try {
				String myResponse = new DefaultHttpClient().execute(
						new HttpGet("http://" + ip + ":9000/api/products"),
						new BasicResponseHandler());

				return new JSONArray(myResponse);

			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}

		@Override
		protected void onPostExecute(JSONArray result) {

			if (result.length() > numberOfProducts) {
				if (numberOfProducts != 0) {
					makeNotificationProduct();
					vibrateLight();
					try {
						Uri notification = RingtoneManager
								.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
						Ringtone r = RingtoneManager.getRingtone(
								getApplicationContext(), notification);
						r.play();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				numberOfProducts = result.length();
			}
		}
	}
	class GetUser extends AsyncTask<Void, Void, JSONArray> {

		@Override
		protected JSONArray doInBackground(Void... params) {
			try {
				String myResponse = new DefaultHttpClient().execute(
						new HttpGet("http://" + ip + ":9000/api/products"),
						new BasicResponseHandler());

				return new JSONArray(myResponse);

			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}

		@Override
		protected void onPostExecute(JSONArray result) {

			if (result.length() > numberOfUsers) {
				if (numberOfUsers != 0) {
					makeNotificationUser();
					vibrateLight();
					try {
						Uri notification = RingtoneManager
								.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
						Ringtone r = RingtoneManager.getRingtone(
								getApplicationContext(), notification);
						r.play();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				numberOfUsers = result.length();
			}
		}
	}
	class GetOrders extends AsyncTask<Void, Void, JSONArray> {

		@Override
		protected JSONArray doInBackground(Void... params) {
			try {
//				String myResponse = new DefaultHttpClient().execute(
//						new HttpGet("http://" + ip + ":9000/api/orders"),
//						new BasicResponseHandler());
				DefaultHttpClient client = new DefaultHttpClient();
				
					
				if (StartPage.cookies != null) {
					for (Cookie cookie : StartPage.cookies) {
						client.getCookieStore().addCookie(cookie);
					}
				}
				String myResponse = client.execute(
					new HttpGet("http://" + ip + ":9000/api/orders"),   
					new BasicResponseHandler()
					);
				
				StartPage.cookies = client.getCookieStore().getCookies();

				return new JSONArray(myResponse);
				
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}

		@Override
		protected void onPostExecute(JSONArray result) {

			if (result.length() > numberOfOrders) {
				if (numberOfOrders != 0) {
					makeNotificationOrder();
					vibrateLight();
					try {
						Uri notification = RingtoneManager
								.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
						Ringtone r = RingtoneManager.getRingtone(
								getApplicationContext(), notification);
						r.play();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				numberOfOrders = result.length();
			}
		}
	}
}