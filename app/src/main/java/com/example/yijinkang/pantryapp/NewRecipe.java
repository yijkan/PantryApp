package com.example.yijinkang.pantryapp;

import android.content.ContentValues;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.SimpleCursorAdapter;

import com.example.yijinkang.pantryapp.R;
import com.example.yijinkang.pantryapp.SQLiteHelper;

public class NewRecipe extends AppCompatActivity {

    private SQLiteHelper dbHelper;
    private SimpleCursorAdapter dataAdapter;
    SQLiteDatabase dbwrite;
    SQLiteDatabase dbread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_recipe);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        dbHelper = new SQLiteHelper(this);
        dbwrite = dbHelper.getWritableDatabase();
        dbread = dbHelper.getReadableDatabase();

    }

    /**
     * Adds a new ingredient to the recipe
     * @param view
     */
    public void newInt(View view) {

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

            dbwrite.insert(SQLiteHelper.TABLE_RECIPES, null, recipeValues);
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
