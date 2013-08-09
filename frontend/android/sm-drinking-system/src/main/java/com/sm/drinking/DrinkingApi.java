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
    //protected String BASE_URL;
    Bitmap bmBef = null;

    /**
	 * constructor
	 *
	 * @param activity
	 */
	public DrinkingApi(Activity activity)
	{
        super(activity);

        /* live environment
        this.BASE_URL = "https://seo-drink-pi/";
        this.urlSuffix = ".json";
        this.rest.setHost("seo-drink-pi");
        this.rest.setPort(3000);
        this.setUserAgent("android");
        http://dev.edi-design.net/sm.php
        */

        /* dev jens
        this.BASE_URL = "http://10.0.101.186:3000/";
        this.rest.setHost("10.0.101.186");
        this.rest.setPort(3000);
        this.setUserAgent("android");
        this.rest.setSsl(false);
        */

        this.BASE_URL = "http://dev.edi-design.net/";
        this.rest.setHost("dev.edi-design.net");
        this.rest.setPort(80);
        this.setUserAgent("android");
        this.rest.setSsl(false);

        //this.acceptAllSslCertificates();
	}

    /**
     * call to get all drinks
     * - name (id)
     * - picture
     * - count
     * @return
     */
	public void getDrinks()
	{
        // this.get("drinks/get");
        this.get("sm.php");
	}

    /**
     * send post request to backend that a drink was checked out
     * @param id
     * @param count
     * @return
     */
    public boolean checkoutDrink(long id, int count)
    {
        return true;
    }


}
