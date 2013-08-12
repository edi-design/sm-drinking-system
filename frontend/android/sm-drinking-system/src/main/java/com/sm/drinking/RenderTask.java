package com.sm.drinking;

import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.util.ByteArrayBuffer;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Vector;


/**
 * Created with IntelliJ IDEA.
 * User: andre
 * Date: 15.03.13
 * Time: 13:58
 * To change this template use File | Settings | File Templates.
 */


public class RenderTask extends AsyncTask<Object, Void, Vector<Drink>> {

    private DrinkingActivity activity;

    public RenderTask(DrinkingActivity activity)
    {
        this.activity = activity;
    }

    @Override
    protected Vector<Drink> doInBackground(Object ... objects)
	{
		//obj = objects[0];
		JSONObject data = (JSONObject) objects[0];
		JSONArray response = null;
		Vector<Drink> drinks = new Vector<Drink>();

		try
		{
			response = data.getJSONArray("response");
		} catch (JSONException e)
		{
			Log.e("parser", "Error parsing outer data " + e.toString());
			return drinks;
		}

		for (int i = 0; i < response.length(); i++)
		{
			try
			{
				JSONObject e = response.getJSONObject(i);

				int id = e.getInt("id");
				String name = e.getString("name");
				int milli_liter = e.getInt("milli_liter");
				int storage_amount = e.getInt("storage_amount");
				long bar_code = Long.parseLong(e.getString("bar_code"));
				int count = e.getInt("count");
				int total = e.getInt("total");
				byte[] image =  getLogoImage(e.getString("image_url"));

				drinks.add(new Drink(bar_code, storage_amount, name, count, total, image));
			}
			catch (Exception e)
			{
				if (e instanceof JSONException)
				{
					Log.e("parser", "Error parsing response data " + e.toString());
				}
				else
				{
					Log.d("ImageManager", "Error: " + e.toString());
				}
			}
		}

		return drinks;
    }

	/**
	 * get image byte-array from url
	 * @param url
	 * @return
	 */
	private byte[] getLogoImage(String url){
		try {
			URL imageUrl = new URL(url);
			URLConnection ucon = imageUrl.openConnection();

			InputStream is = ucon.getInputStream();
			BufferedInputStream bis = new BufferedInputStream(is);

			ByteArrayBuffer baf = new ByteArrayBuffer(500);
			int current = 0;
			while ((current = bis.read()) != -1) {
				baf.append((byte) current);
			}

			return baf.toByteArray();
		} catch (Exception e) {
			Log.d("ImageManager", "Error: " + e.toString());
		}
		return null;
	}

    protected void onPostExecute(Vector<Drink> drinks)
	{
        activity.renderDrinks(drinks);
    }
}
