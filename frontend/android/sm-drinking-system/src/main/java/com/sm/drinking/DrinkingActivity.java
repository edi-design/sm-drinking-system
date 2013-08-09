package com.sm.drinking;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.manavo.rest.RestCallback;
import com.sm.drinking.util.SystemUiHider;

import org.json.JSONArray;

import java.util.Vector;

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

    public final void setStatus(String text)
    {
        ((TextView)findViewById(R.id.state)).setText("Status: "+text);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_drinking);

        this.getDrinks();

        //logoImage.setImageBitmap(BitmapFactory.decodeByteArray(currentAccount.accImage, 0, this.accImage.length));
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
            public void success(Object obj) {
                JSONArray data = (JSONArray)obj;

                Vector <Drink> drinks = new Vector<Drink>();
                byte[] image = null;
                for (int i=0; i< data.length(); i++)
                {
                    drinks.add(new Drink(54491229, 20, "Coca Cola", 10, 20, image));
                    drinks.add(new Drink(4029764001807L, 20, "Club Mate", 5, 35, image));
                    drinks.add(new Drink(4015732007155L, 12, "Wasser still", 54, 352, image));
                }

                renderDrinks(drinks);
            }
        });
        this.api.getDrinks();
    }

    public void renderDrinks(Vector<Drink> returned_drinks)
    {
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        LinearLayout ll = (LinearLayout) findViewById(R.id.lines);
        drinks = new Vector<DrinkContainer>();

        for (final Drink d : returned_drinks) {
            LinearLayout ll2 = new LinearLayout(this);
            ll2.setOrientation(LinearLayout.HORIZONTAL);
            //ll2.set

            final Button reset = new Button(this);
            final Button logoImage = new Button(this);
            final Button logoImageStorage = new Button(this);
            final TextView current = new TextView(this);
            final TextView total = new TextView(this);

            reset.setMinHeight(20);
            reset.setMinWidth(30);

            reset.setTextSize(12);
            reset.setPadding(3,3,3,3);

            drinks.add(new DrinkContainer(d, logoImage, logoImageStorage, current, total, reset));

            reset.setText("R");
            logoImage.setText(d.getName());
            logoImageStorage.setText(new Integer(d.getStorageAmount()).toString());
            current.setText(new Integer(d.getCurrent()).toString());
            total.setText("/" + new Integer(d.getTotal()).toString());

            ll2.addView(reset);
            ll2.addView(logoImage);
            ll2.addView(logoImageStorage);
            ll2.addView(current);
            ll2.addView(total);
            ll.addView(ll2, lp);

            logoImage.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {

                    performDrinkCheckout(d, 1, current);
                }
            });

            logoImageStorage.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {

                    performDrinkCheckout(d, d.getStorageAmount(), current);
                }
            });

            reset.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {

                    performDrinkCheckout(d, -1, current);
                }
            });
        }
    }

    public void performDrinkCheckout(Drink d, int amount, TextView current)
    {
        // add api call
        if (api.checkoutDrink(d.getId(), amount)) {

            if(amount == -1)
            {
                setStatus(d.getName()+" zurückgelegt");
            }
            else
            {
                setStatus(d.getName()+" entnommen ("+amount+")!");
            }

            Integer curr = new Integer(Integer.parseInt(current.getText().toString()) - amount);

            if(curr < 0)
            {
                curr = 0;
                setStatus("Fehlbestand ermittelt.");
            }

            d.setCurrent(curr);
            current.setText(curr.toString());
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
                        this.setStatus("Getränk nicht gefunden (" + barcode + ")");
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
