package com.sm.drinking;

import android.app.Activity;

import com.manavo.rest.RestApi;

import java.util.Vector;

/**
 * Created with IntelliJ IDEA.
 * User: andre
 * Date: 13.06.13
 * Time: 12:57
 * This calls provides all methods provided by the tt-rss api
 * and returns usuable objects
 */
public class DrinkingApi
{
    private final String BASE_URL;

    /**
	 * constructor
	 *
	 * @param activity
	 */
	public DrinkingApi(Activity activity)
	{
        //ApiTask ApiTask = new ApiTask(activity);
		// super(activity);

		this.BASE_URL = "https://seo-drink-pi/backend/";
		//this.urlSuffix = ".json";
	}

	/**
     * call to get all drinks
     * - name (id)
     * - picture
     * - count
     */
	public Vector<Drink> getDrinks()
	{
        Vector <Drink> drinks = new Vector<Drink>();
        drinks.add(new Drink("Coca Cola", 10, 20));
        drinks.add(new Drink("Club Mate", 5, 35));

        return drinks;
	}


}
