package com.example.yijinkang.pantryapp;

import android.database.Cursor;

/**
 * Created by aidan on 9/17/16.
 */
public class Item {
    private String name;
    private Number qty;
    private String unit;

    /**
     * Constructs a new {@code Item} with the given name and quantity (no units).
     */
    public Item(String name, Number qty) {
        this(name, qty, null);
    }

    /**
     * Constructs a new {@code Item} with the given name, quantity, and units.
     */
    public Item(String name, Number qty, String unit) {
        this.name = name;
        this.qty = qty;
        this.unit = unit;
    }

    public Item(Cursor c) {
        this.name = c.getString(1);
        this.qty = c.getFloat(2);
        this.unit = c.getString(3);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Number getQty() {
        return qty;
    }

    public void setQty(Number qty) {
        this.qty = qty;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    /**
     * Returns a string in the format {@code qty [unit] item_name}.
     */
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(qty).append(' ');
        if (unit != null) {
            sb.append(unit).append(' ');
        }
        return sb.append(name).toString();
    }
}
