package com.sm.drinking;

/**
 * Created by andre on 09.08.13.
 */
public class Drink {
    private long id;
    private String name;
    private int storage_amount;
    private int current;
    private int total;

    public int getStorageAmount() {
        return storage_amount;
    }

    public void setStorageAmount(int storage_amount) {
        this.storage_amount = storage_amount;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    private byte[] image;

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

    public Drink(long id, int storage_amount, String name, int current, int total, byte[] image) {
        this.id = id;
        this.storage_amount = storage_amount;
        this.name = name;
        this.current = current;
        this.total = total;
        this.image = image;
    }
}
