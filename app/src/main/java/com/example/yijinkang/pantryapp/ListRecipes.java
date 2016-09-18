package com.example.yijinkang.pantryapp;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

public class ListRecipes extends AppCompatActivity implements AdapterView.OnItemClickListener {
    ListView recipeList;
    ArrayAdapter recipesAdapter;
    SQLiteHelper dbHelper;
    SQLiteDatabase dbread;

    //String[] recipeNames = getResources().getStringArray(R.array.recipeNames);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_recipes);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        List<String> recipeNames = new ArrayList<>();

        //Pulls stored recipes from the database.
        dbHelper = new SQLiteHelper(this);
        dbread = dbHelper.getReadableDatabase();
        //the String[] specifies which column you want from the database. if it's empty, it returns all columns
        Cursor c = dbread.rawQuery("select * from " + SQLiteHelper.TABLE_RECIPES, new String[]{});

        while (c.moveToNext()) { //returns true if successful
            recipeNames.add(c.getString(1));
        }
        //finds the (ListView) recipeList object inside the GUI
        recipeList = (ListView) findViewById(R.id.listedRecipes);


        //adapter pulls String names from recipeNames, dumps into adapter
        recipesAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, recipeNames);
        //adapter assigns data to (ListView) recipeList
        recipeList.setAdapter(recipesAdapter);

        recipeList.setOnItemClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_list_recipes, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onItemClick(AdapterView<?> adapter, View view, int position, long id) {
        Intent i = new Intent(this, ViewRecipe.class);
        startActivity(i);
    }

}