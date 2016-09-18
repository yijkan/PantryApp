package com.example.yijinkang.pantryapp;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by aidan on 9/17/16.
 */
public class Recipe {

    String name;
    List<Item> ingredients;
    String instructions; // or android.text.Spanned
    String mealType;

    public Recipe(String name, List<Item> ingredients, String instructions) {
        this.name = name;
        this.ingredients = ingredients;
        this.instructions = instructions;
    }

    public Recipe(SQLiteDatabase db, int recipeID) {
        Cursor recipeBasicInfo = db.rawQuery("select * from " + SQLiteHelper.TABLE_RECIPES +
                " where " + SQLiteHelper.COLUMN_ID + " = " + recipeID, new String[0]);

        recipeBasicInfo.moveToFirst();
        name = recipeBasicInfo.getString(1);
        instructions = recipeBasicInfo.getString(2);
        mealType = recipeBasicInfo.getString(3);

        Cursor recipeIngredients = db.rawQuery("select * from " + SQLiteHelper.TABLE_INGR +
                " where " + SQLiteHelper.COLUMN_RECIPE + " = " + recipeID +
                " order by " + SQLiteHelper.COLUMN_FOOD, new String[0]);

        ingredients = new ArrayList<>();

        while (recipeIngredients.moveToNext()) {
            Item ingr = new Item(recipeIngredients, Item.TABLE_INGREDIENTS);
            ingredients.add(ingr);
        }
    }

    public String getName() {
        return this.name;
    }

}
