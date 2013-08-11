package com.sm.drinking;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.manavo.rest.RestCallback;
import com.sm.drinking.util.SystemUiHider;

import org.apache.http.util.ByteArrayBuffer;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Vector;

import static android.view.View.TEXT_ALIGNMENT_CENTER;
import static android.view.View.TEXT_ALIGNMENT_GRAVITY;
import static android.widget.GridLayout.FILL;
import static android.widget.GridLayout.Spec;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 *
 * @see SystemUiHider
 */
public class DrinkingActivity extends Activity {
    /**
     * Whether or not the system UI should be auto-hidden after
     * {@link #AUTO_HIDE_DELAY_MILLIS} milliseconds.
     */
    private static final boolean AUTO_HIDE = true;

    /**
     * If {@link #AUTO_HIDE} is set, the number of milliseconds to wait after
     * user interaction before hiding the system UI.
     */
    private static final int AUTO_HIDE_DELAY_MILLIS = 3000;

    /**
     * If set, will toggle the system UI visibility upon interaction. Otherwise,
     * will show the system UI visibility upon interaction.
     */
    private static final boolean TOGGLE_ON_CLICK = true;

    /**
     * The flags to pass to {@link SystemUiHider#getInstance}.
     */
    private static final int HIDER_FLAGS = SystemUiHider.FLAG_HIDE_NAVIGATION;

    /**
     * The instance of the {@link SystemUiHider} for this activity.
     */
    private SystemUiHider mSystemUiHider;

    private Vector<DrinkContainer> drinks;
    private DrinkingApi api;
    private long barcode = 0;

	/**
	 * set text for status view
	 * @param text
	 */
    public final void setStatus(String text)
    {
        ((TextView)findViewById(R.id.state)).setText(getString(R.string.status) + " " + text);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_drinking);

        this.getDrinks();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.about:
                Context context = getApplicationContext();
                CharSequence text = "best team ever!";
                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
                return true;
            case R.id.reload:
                this.getDrinks();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void getDrinks()
    {
        this.api = new DrinkingApi(this);

        this.api.setCallback(new RestCallback() {
            public void success(Object obj)
			{
				callRenderTask(obj);
			}
        });
        this.api.getDrinks();
    }

	private void callRenderTask(Object obj)
	{
		// open Render Task
		new RenderTask(this).execute(obj);
	}

	/**
	 * sets all drinks from api call to linear layout
	 * @param returned_drinks
	 */
    public void renderDrinks(Vector<Drink> returned_drinks)
    {
        ScrollView view_scroll = (ScrollView) findViewById(R.id.scrollView);
		view_scroll.setForegroundGravity(Gravity.CENTER_VERTICAL);

		GridLayout layout_grid = (GridLayout) findViewById(R.id.grid);
		layout_grid.setColumnCount(3);

		// reset layout, so after reload there are only the loaded elements
		layout_grid.removeAllViewsInLayout();
        drinks = new Vector<DrinkContainer>();
		int col_position = 0;
		int col_span = 1;
		int row_position = 0;
		int row_span = 1;
		int col_limit = 2;

        for (final Drink d : returned_drinks) {
            LinearLayout layout_drink = new LinearLayout(this);
            layout_drink.setOrientation(LinearLayout.VERTICAL);
			layout_drink.setBackgroundResource(R.drawable.border);
			//layout_drink.setPadding(5,5,5,5);
			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
			layout_drink.setLayoutParams(params);

            final Button btn_reset = new Button(this);
            final Button btn_name = new Button(this);
            final Button btn_storage = new Button(this);
			final TextView tv_stock = new TextView(this);
			final ImageButton img_drink = new ImageButton(this);

			/*
			 * configure image of drink
			 */
			Bitmap bmp_image = BitmapFactory.decodeByteArray(d.getImage(), 0, d.getImage().length);
			img_drink.setImageBitmap(bmp_image);
			img_drink.setMaxWidth(100);
			img_drink.setScaleType(ImageView.ScaleType.CENTER);

            btn_reset.setText(getString(R.string.return_drink));
            btn_name.setText(d.getName());

            btn_storage.setText(new Integer(d.getStorageAmount()).toString() + " " + getString(R.string.bottles));
			String stock = (new Integer(d.getCurrent()).toString()) + " " + getString(R.string.divider) + " " + (new Integer(d.getTotal()).toString());
			tv_stock.setText(stock);
			tv_stock.setGravity(Gravity.CENTER_HORIZONTAL);

			/*
			 * set field config in grid_layout
			 */
			Spec row = GridLayout.spec(row_position, row_span);
			Spec col = GridLayout.spec(col_position, col_span);
			GridLayout.LayoutParams layout_params = new GridLayout.LayoutParams(row, col);
			layout_params.setMargins(3,5,3,5);

			/**
			 * add elements
			 */
            layout_drink.addView(btn_reset);
			layout_drink.addView(tv_stock);
			layout_drink.addView(img_drink);
            layout_drink.addView(btn_name);
            layout_drink.addView(btn_storage);
			layout_drink.setLayoutParams(layout_params);
			layout_grid.addView(layout_drink, layout_params);

			/**
			 * set next column or row
			 */
			if (col_position == col_limit)
			{
				col_position = 0;
				row_position = row_position + 1;
			}
			else
			{
				col_position = col_position + 1;
			}

            btn_name.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {

                    performDrinkCheckout(d, 1, tv_stock);
                }
            });

            btn_storage.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {

                    performDrinkCheckout(d, d.getStorageAmount(), tv_stock);
                }
            });

            btn_reset.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {

                    performDrinkCheckout(d, -1, tv_stock);
                }
            });

			img_drink.setOnClickListener(new View.OnClickListener() {
				public void onClick(View view) {

					performDrinkCheckout(d, 1, tv_stock);
				}
			});
        }
    }

    public void performDrinkCheckout(Drink d, int amount, TextView tv_stock)
    {
        // add api call
        if (api.checkoutDrink(d.getId(), amount))
		{
            if(amount == -1)
            {
                setStatus(d.getName() + " " + getString(R.string.put_back));
            }
            else
            {
                setStatus(d.getName() + " " + getString(R.string.took) + " (" + amount + ")!");
            }

            Integer curr = new Integer(d.getCurrent() - amount);

            if(curr < 0)
            {
                curr = 0;
                setStatus(getString(R.string.stock_error));
            }

            d.setCurrent(curr);
			String stock = (new Integer(d.getCurrent()).toString()) + " " + getString(R.string.divider) + " " + (new Integer(d.getTotal()).toString());
			tv_stock.setText(stock);
        }
    }

    public DrinkContainer findDrink(long id) {
        for (DrinkContainer d : drinks) {
            if (d.getDrink().getId() == id) {
                return d;
            }
        }

        return null;
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_UP) {
            byte keyValue = 0;

            switch (event.getKeyCode()) {
                case KeyEvent.KEYCODE_0:
                    keyValue = 0;
                    break;
                case KeyEvent.KEYCODE_1:
                    keyValue = 1;
                    break;
                case KeyEvent.KEYCODE_2:
                    keyValue = 2;
                    break;
                case KeyEvent.KEYCODE_3:
                    keyValue = 3;
                    break;
                case KeyEvent.KEYCODE_4:
                    keyValue = 4;
                    break;
                case KeyEvent.KEYCODE_5:
                    keyValue = 5;
                    break;
                case KeyEvent.KEYCODE_6:
                    keyValue = 6;
                    break;
                case KeyEvent.KEYCODE_7:
                    keyValue = 7;
                    break;
                case KeyEvent.KEYCODE_8:
                    keyValue = 8;
                    break;
                case KeyEvent.KEYCODE_9:
                    keyValue = 9;
                    break;
                case KeyEvent.KEYCODE_ENTER:
                    DrinkContainer d = this.findDrink(barcode);

                    if (d == null)
                    {
                        this.setStatus(getString(R.string.not_found) + " (" + barcode + ")");
                    } else {
                        d.buttonSingle.performClick();
                        //this.setStatus(d.getDrink().getName()+" entnommen!");
                    }

                    barcode = 0;
                    return false;
            }

            barcode = barcode * 10 + keyValue;
        }

        return false;
    }
}
