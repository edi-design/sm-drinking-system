package com.sm.drinking;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sm.drinking.util.SystemUiHider;

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

    private Vector<Drink> drinks;
    private DrinkingApi api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_drinking);

        this.api = new DrinkingApi(this);
        this.drinks = api.getDrinks();

        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        LinearLayout ll = (LinearLayout) findViewById(R.id.lines);

        for (final Drink d : this.drinks) {
            LinearLayout ll2 = new LinearLayout(this);
            ll2.setOrientation(LinearLayout.HORIZONTAL);
            //ll2.set

            final Button logoImage = new Button(this);
            final Button logoImageStorage = new Button(this);
            final TextView current = new TextView(this);
            final TextView total = new TextView(this);
            logoImage.setText(d.getName());
            logoImageStorage.setText(d.getName()+"("+(new Integer(d.getStorageAmount()).toString())+")");
            current.setText(new Integer(d.getCurrent()).toString());
            total.setText("/" + new Integer(d.getTotal()).toString());

            ll2.addView(logoImage);
            ll2.addView(logoImageStorage);
            ll2.addView(current);
            ll2.addView(total);
            ll.addView(ll2, lp);

            logoImage.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {

                    int amount = 1;

                    // add api call
                    if (api.checkoutDrink(d.getId(), amount)) {
                        Integer curr = new Integer(Integer.parseInt(current.getText().toString()) - amount);
                        d.setCurrent(curr);
                        current.setText(curr.toString());
                    }
                }
            });

            logoImageStorage.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {

                    int amount = d.getStorageAmount();

                    // add api call
                    if (api.checkoutDrink(d.getId(), amount)) {
                        Integer curr = new Integer(Integer.parseInt(current.getText().toString()) - amount);
                        d.setCurrent(curr);
                        current.setText(curr.toString());
                    }
                }
            });
        }

        //logoImage.setImageBitmap(BitmapFactory.decodeByteArray(currentAccount.accImage, 0, this.accImage.length));
    }
}
