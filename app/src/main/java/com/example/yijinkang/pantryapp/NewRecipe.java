package com.example.yijinkang.pantryapp;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import java.util.ArrayList;
import java.util.List;

public class NewRecipe extends AppCompatActivity implements View.OnFocusChangeListener {

    private SQLiteHelper dbHelper;
    private SimpleCursorAdapter dataAdapter;
    SQLiteDatabase dbwrite;
    SQLiteDatabase dbread;

    EditText recipeNameField;
    EditText instructionsField;
    EditText foodTypeField;
    EditText newIngEditText;
    EditText qtyEditText;
    EditText unitEditText;

    List<Item> ingredients;
    private ArrayAdapter<Item> ingredientsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_recipe);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        dbHelper = new SQLiteHelper(this);
        dbwrite = dbHelper.getWritableDatabase();
        dbread = dbHelper.getReadableDatabase();

        recipeNameField = (EditText) findViewById(R.id.recipeName);
        recipeNameField.setOnFocusChangeListener(this);
        instructionsField = (EditText) findViewById(R.id.instructions);
        instructionsField.setOnFocusChangeListener(this);
        foodTypeField = (EditText) findViewById(R.id.type);
        foodTypeField.setOnFocusChangeListener(this);
        newIngEditText = (EditText) findViewById(R.id.newIng);
        newIngEditText.setOnFocusChangeListener(this);
        qtyEditText = (EditText) findViewById(R.id.qty);
        qtyEditText.setOnFocusChangeListener(this);
        unitEditText = (EditText) findViewById(R.id.unit);
        unitEditText.setOnFocusChangeListener(this);

        ingredients = new ArrayList<Item>();
        ListView listview = (ListView) findViewById(R.id.ingredients);
        ingredientsAdapter = new ArrayAdapter<Item>(this, android.R.layout.simple_list_item_1, ingredients);
        // Set the ListView to the adapter
        listview.setAdapter(ingredientsAdapter);

//        Log.d("","*****");
//
//        Cursor recipes = dbread.rawQuery("select * from " + SQLiteHelper.TABLE_RECIPES, new String[]{});
//        while (recipes.moveToNext()) {
//            Log.d("loaded from database", recipes.getInt(0) + recipes.getString(1) + "\n");
//        }
//
//        Cursor ingredients = dbread.rawQuery("select * from " + SQLiteHelper.TABLE_INGR, new String[]{});
//        while (ingredients.moveToNext()) {
//            Log.d("loaded from database", ingredients.getInt(0) + ingredients.getString(1) + "\n");
//        }
    }

    @Override
    public void onFocusChange(View view, boolean hasFocus) {
        if (!hasFocus) {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    /**
     * Adds a new ingredient to the recipe
     * @param view
     */
    public void newIng(View view) {
        Log.d("function call", "newIng");

        String item = newIngEditText.getText().toString();
        String qtyStr = qtyEditText.getText().toString();
        String unit = unitEditText.getText().toString();
        Number qty;

        try {
            qty = Double.parseDouble(qtyStr);
            ingredients.add(new Item(item, qty, unit));
            ingredientsAdapter.notifyDataSetChanged();
            Log.d("","data set changed " + ingredients.size() + " " + ingredientsAdapter.getCount());

            newIngEditText.setText("");
            qtyEditText.setText("");
            unitEditText.setText("");
        } catch (NumberFormatException e) {
            // bleh
        }
    }

    /**
     * saves the inputted recipe to the database
     * (TODO where does it go afterward?)
     * @param view
     */
    public void save(View view) {
        String name = recipeNameField.getText().toString();
        String instr = instructionsField.getText().toString();
        String type = foodTypeField.getText().toString();

        ContentValues recipeValues = new ContentValues();
        recipeValues.put(SQLiteHelper.COLUMN_RECIPENAME, name);
        recipeValues.put(SQLiteHelper.COLUMN_INSTR, instr);
        recipeValues.put(SQLiteHelper.COLUMN_TYPE, type);

        long recipeID = dbwrite.insert(SQLiteHelper.TABLE_RECIPES, null, recipeValues);

        if (recipeID < 0) {
            // TODO some error handling
            return;
        }

        ArrayList<ContentValues> ingredientCVs = new ArrayList<ContentValues>();

        // for loop...
        for (Item ing : ingredients) {
            ContentValues itemValues = new ContentValues();
            itemValues.put(SQLiteHelper.COLUMN_RECIPE, recipeID);
            itemValues.put(SQLiteHelper.COLUMN_FOOD, ing.getName());
            itemValues.put(SQLiteHelper.COLUMN_QTY, (Double) ing.getQty());
            itemValues.put(SQLiteHelper.COLUMN_UNIT, ing.getUnit());

            ingredientCVs.add(itemValues);
        }

        try {
            dbwrite.beginTransaction();

            for (ContentValues ingredientCV : ingredientCVs) {
                dbwrite.insert(SQLiteHelper.TABLE_INGR, null, ingredientCV);
            }

            dbwrite.setTransactionSuccessful();
        } catch (SQLException e) {
            // TODO error handling
        } finally {
            dbwrite.endTransaction();
        }

        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }

    /**
     * cancels the recipe input (TODO and goes where?)
     * @param view
     */
    public void cancel(View view) {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }
}
