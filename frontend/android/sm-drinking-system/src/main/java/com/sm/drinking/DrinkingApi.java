package com.sm.drinking;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import com.manavo.rest.RestApi;

import java.io.ByteArrayOutputStream;
import java.util.Vector;

/**
 * Created with IntelliJ IDEA.
 * User: andre
 * Date: 13.06.13
 * Time: 12:57
 * This calls provides all methods provided by the tt-rss api
 * and returns usuable objects
 */
public class DrinkingApi extends RestApi
{
    private final String BASE_URL;
    Bitmap bmBef = null;

    /**
	 * constructor
	 *
	 * @param activity
	 */
	public DrinkingApi(Activity activity)
	{
        //ApiTask ApiTask = new ApiTask(activity);
		super(activity);

		this.BASE_URL = "https://seo-drink-pi/backend/";
		//this.urlSuffix = ".json";
	}

    /**
     * call to get all drinks
     * - name (id)
     * - picture
     * - count
     * @return
     */
	public Vector<Drink> getDrinks()
	{
        byte[] image = null;

        Vector <Drink> drinks = new Vector<Drink>();
        drinks.add(new Drink(1, 20, "Coca Cola", 10, 20, image));
        drinks.add(new Drink(2, 20, "Club Mate", 5, 35, image));
        drinks.add(new Drink(3, 12, "Wasser still", 54, 352, image));

        return drinks;
	}

    /**
     * send post request to backend that a drink was checked out
     * @param id
     * @param count
     * @return
     */
    public boolean checkoutDrink(int id, int count)
    {
        return true;
    }


}
