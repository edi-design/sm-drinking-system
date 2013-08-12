package com.sm.drinking;

import android.widget.TextView;
import android.widget.Button;

/**
 * Created by StefanFranke on 09.08.13.
 */
public class DrinkContainer {
    Drink drink;
    Button buttonSingle;

    public DrinkContainer(Drink drink, Button buttonSingle) {
        this.drink = drink;
        this.buttonSingle = buttonSingle;
    }

    public Drink getDrink() {
        return drink;
    }

    public void setDrink(Drink drink) {
        this.drink = drink;
    }

    public Button getButtonSingle() {
        return buttonSingle;
    }

    public void setButtonSingle(Button buttonSingle) {
        this.buttonSingle = buttonSingle;
    }
}
