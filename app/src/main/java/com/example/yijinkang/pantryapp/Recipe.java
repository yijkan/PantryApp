package com.example.yijinkang.pantryapp;

import android.database.Cursor;

import java.util.List;

/**
 * Created by aidan on 9/17/16.
 */
public class Recipe {

    String name;
    List<Item> ingredients;
    String instructions; // or android.text.Spanned

    public Recipe(String name, List<Item> ingredients, String instructions) {
        this.name = name;
        this.ingredients = ingredients;
        this.instructions = instructions;
    }

    public Recipe(Cursor c) {
        this.name = c.getString(1);
        // this.ingredients = ;
        this.instructions = c.getString(2);
    }

    public String getName() {
        return this.name;
    }

}
