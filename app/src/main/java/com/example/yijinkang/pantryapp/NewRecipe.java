package com.example.yijinkang.pantryapp;

import android.content.ContentValues;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import java.util.ArrayList;
import java.util.List;

public class NewRecipe extends AppCompatActivity {

    private SQLiteHelper dbHelper;
    private SimpleCursorAdapter dataAdapter;
    private ArrayAdapter<Item> arrayAdapter;
    SQLiteDatabase dbwrite;
    SQLiteDatabase dbread;
    List<Item> ingredients;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_recipe);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        dbHelper = new SQLiteHelper(this);
        dbwrite = dbHelper.getWritableDatabase();
        dbread = dbHelper.getReadableDatabase();

        ingredients = new ArrayList<Item>();
        ListView listview = (ListView) findViewById(R.id.ingredients);
        arrayAdapter = new ArrayAdapter<Item>(this, android.R.layout.simple_list_item_1, ingredients);
        // Set the ListView to the adapter
        listview.setAdapter(arrayAdapter);
    }

    /**
     * Adds a new ingredient to the recipe
     * @param view
     */
    public void newIng(View view) {
        Log.d("function call", "newIng");

        EditText newIngEditText = (EditText) findViewById(R.id.newIng);
        EditText qtyEditText = (EditText) findViewById(R.id.qty);
        EditText unitEditText = (EditText) findViewById(R.id.unit);

        String item = newIngEditText.getText().toString();
        String qtyStr = qtyEditText.getText().toString();
        String unit = unitEditText.getText().toString();
        Number qty;

        try {
            qty = Double.parseDouble(qtyStr);
            ingredients.add(new Item(item, qty, unit));
            arrayAdapter.notifyDataSetChanged();
            Log.d("","data set changed " + ingredients.size() + " " + arrayAdapter.getCount());

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
        String name = ((EditText) findViewById(R.id.recipeName)).getText().toString();
        String instr = ((EditText) findViewById(R.id.instructions)).getText().toString();

        ContentValues recipeValues = new ContentValues();
        recipeValues.put(SQLiteHelper.COLUMN_RECIPENAME, name);
        recipeValues.put(SQLiteHelper.COLUMN_INSTR, instr);



        // TODO ingredients?

        // for loop...
        ContentValues ingValues = new ContentValues();
        try {
            dbwrite.beginTransaction();

//            dbwrite.insert(SQLiteHelper.TABLE_RECIPES, null, recipeValues);
            /// etc
            dbwrite.setTransactionSuccessful();
        } catch (SQLException e) {

        } finally {
            dbwrite.endTransaction();
        }


//        this.getContentResolver().bulkInsert(SQLiteHelper.TABLE_INGR, values);

    }

    /**
     * cancels the recipe input (TODO and goes where?)
     * @param view
     */
    public void cancel(View view) {

    }
}
