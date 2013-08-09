package com.sm.drinking;

/**
 * Created by andre on 09.08.13.
 */
public class Drink {
    private String name;
    private int current;
    private int total;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCurrent() {
        return current;
    }

    public void setCurrent(int current) {
        this.current = current;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public Drink(String name, int current, int total) {
        this.name = name;
        this.current = current;
        this.total = total;
    }
}
