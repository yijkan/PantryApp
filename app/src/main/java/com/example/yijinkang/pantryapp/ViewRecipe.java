package com.example.yijinkang.pantryapp;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import com.example.yijinkang.pantryapp.R;

import in.uncod.android.bypass.Bypass;

public class ViewRecipe extends AppCompatActivity {

    private SQLiteHelper dbHelper;
    private SimpleCursorAdapter dataAdapter;
    SQLiteDatabase dbread;

    Recipe myRecipe;

    // TODO: parent needs to send the recipe ID in the intent
    private Intent parentIntent = getIntent();
    int recipeID = parentIntent.getIntExtra("recipeID", -1);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_recipe);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        dbHelper = new SQLiteHelper(this);
        dbread = dbHelper.getReadableDatabase();

        myRecipe = new Recipe(dbread, recipeID);

        toolbar.setTitle(myRecipe.name);

        CharSequence instructionsMarkdown = new Bypass().markdownToSpannable(myRecipe.instructions);

        TextView instrTextBox = (TextView) findViewById(R.id.instructions);
        instrTextBox.setText(instructionsMarkdown);
    }
}
