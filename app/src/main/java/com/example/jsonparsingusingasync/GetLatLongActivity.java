package com.example.jsonparsingusingasync;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class GetLatLongActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		new DataLongOperationAsynchTask().execute();

	}

	private class DataLongOperationAsynchTask extends
			AsyncTask<String, Void, String[]> {
		ProgressDialog dialog = new ProgressDialog(GetLatLongActivity.this);

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			dialog.setMessage("Please wait...");
			dialog.setCanceledOnTouchOutside(false);
			dialog.show();
		}

		@Override
		protected String[] doInBackground(String... params) {
			String response;
			try {
				response = getLatLongByURL("http://maps.google.com/maps/api/geocode/json?address=mumbai&sensor=false");
				System.out.println("=========response=========" + response);
				return new String[] { response };
			} catch (Exception e) {
				System.out.println("=========catch=========" + e.toString());
				return new String[] { "error" };
			}
		}

		@Override
		protected void onPostExecute(String... result) {
			try {
				JSONObject jsonObject = new JSONObject(result[0]);

				double lng = ((JSONArray) jsonObject.get("results"))
						.getJSONObject(0).getJSONObject("geometry")
						.getJSONObject("location").getDouble("lng");

				double lat = ((JSONArray) jsonObject.get("results"))
						.getJSONObject(0).getJSONObject("geometry")
						.getJSONObject("location").getDouble("lat");

				// Log.d("latitude", "" + lat);
				// Log.d("longitude", "" + lng);
				Toast.makeText(
						GetLatLongActivity.this,
						String.valueOf("Latitude:" + lat + "  Longitude:" + lng),
						Toast.LENGTH_LONG).show();
			} catch (JSONException e) {
				e.printStackTrace();
			}
			if (dialog.isShowing()) {
				dialog.dismiss();
			}
		}
	}

	public String getLatLongByURL(String requestURL) {
		URL url;
		String response = "";
		try {
			url = new URL(requestURL);

			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setReadTimeout(15000);
			conn.setConnectTimeout(15000);
			conn.setRequestMethod("GET");
			conn.setDoInput(true);
			conn.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded");
			conn.setDoOutput(true);
			int responseCode = conn.getResponseCode();

			if (responseCode == HttpsURLConnection.HTTP_OK) {
				String line;
				BufferedReader br = new BufferedReader(new InputStreamReader(
						conn.getInputStream()));
				while ((line = br.readLine()) != null) {
					response += line;
				}
				System.out
						.println("=========getLatLongByURL:IFFFF:response========="
								+ response);
			} else {
				response = "";
				System.out
						.println("=========getLatLongByURL:ELSE:response========="
								+ response);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return response;
	}

}
