package com.sm.drinking;

import android.widget.TextView;
import android.widget.Button;

/**
 * Created by StefanFranke on 09.08.13.
 */
public class DrinkContainer {
    Drink drink;
    Button buttonSingle;
    Button buttonStorage;
    Button reset;
    TextView current;
    TextView total;

    public DrinkContainer(Drink drink, Button buttonSingle, Button buttonStorage, TextView current, TextView total, Button reset) {
        this.drink = drink;
        this.buttonSingle = buttonSingle;
        this.buttonStorage = buttonStorage;
        this.current = current;
        this.total = total;
        this.reset = reset;
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

    public Button getButtonStorage() {
        return buttonStorage;
    }

    public void setButtonStorage(Button buttonStorage) {
        this.buttonStorage = buttonStorage;
    }

    public TextView getCurrent() {
        return current;
    }

    public void setCurrent(TextView current) {
        this.current = current;
    }

    public TextView getTotal() {
        return total;
    }

    public void setTotal(TextView total) {
        this.total = total;
    }
}
