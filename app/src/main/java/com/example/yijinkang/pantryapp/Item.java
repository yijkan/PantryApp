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

    public static final int TABLE_INGREDIENTS = 1;
    public static final int TABLE_GROCERIES = 0;

    /**
     * Constructs a new {@code Item} from a SQLite result set.
     * @param c A {@link Cursor} with the result of a query to the specified {@code table}
     * @param table either {@link #TABLE_INGREDIENTS} or {@link #TABLE_GROCERIES}
     */
    public Item(Cursor c, int table) {
        this.name = c.getString(0 + table);
        this.qty = c.getFloat(1 + table);
        this.unit = c.getString(2 + table);
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
