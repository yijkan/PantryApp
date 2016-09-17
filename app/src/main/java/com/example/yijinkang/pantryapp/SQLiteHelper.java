package com.example.yijinkang.pantryapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Handles the creation and deletion of the database
 * Call getReadableDatabase() or getWritableDatabase() on an instance of this class to get a SQLiteDatabase object
 * SQLiteDatabase provides  insert(), update(), delete(), and execSQL() for writing to database;
 *                          rawQuery() and query() for reading, which return a Cursor object
 */

public class SQLiteHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "PantryApp.db";
    /**
     * constants that specify table and column names
     */
    public static final String TABLE_RECIPES = "Recipes";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_RECIPENAME = "name";
    public static final String COLUMN_INSTR = "instructions";
    public static final String COLUMN_TYPE = "type";

    public static final String TABLE_INGR = "Ingredients";
    public static final String COLUMN_RECIPE = "recipe_id";
    public static final String COLUMN_FOOD = "name";
    public static final String COLUMN_QTY = "quantity";
    public static final String COLUMN_UNIT = "unit";

    public static final String TABLE_GROCERIES = "Groceries";

    /**
     * constants for database actions
     */
    private static final String SQL_CREATE_TABLES =
            "CREATE TABLE " + TABLE_RECIPES + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_RECIPENAME + " TEXT NOT NULL, " +
                    COLUMN_INSTR + " TEXT NULL, " +
                    COLUMN_TYPE + " TEXT NOT NULL" +
                    " ); " +
            "CREATE TABLE " + TABLE_INGR + " (" +
                    COLUMN_RECIPE + " INTEGER NOT NULL, " +
                    COLUMN_FOOD + " TEXT NOT NULL, " +
                    COLUMN_QTY + " REAL NOT NULL, " +
                    COLUMN_UNIT + " TEXT NOT NULL" +
                    " );" +
            "CREATE TABLE " + TABLE_GROCERIES + " (" +
                    COLUMN_FOOD + " TEXT NOT NULL, " +
                    COLUMN_QTY + " REAL NOT NULL, " +
                    COLUMN_UNIT + " TEXT NOT NULL" +
                    " );"
            ;
    private static final String SQL_DELETE_DATABASE =
            "DROP TABLE IF EXISTS " + TABLE_RECIPES + "; " +
            "DROP TABLE IF EXISTS " + TABLE_INGR + "; " +
            "DROP TABLE IF EXISTS " + TABLE_GROCERIES + "; ";

    /**
     * constructor
     * @param context
     */
    public SQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        Log.d(SQLiteHelper.class.getName(), "creating database from string: " + SQL_CREATE_TABLES);
//        db.execSQL(SQL_CREATE_DATABASE);
        db.execSQL(SQL_CREATE_TABLES);
        Log.d(SQLiteHelper.class.getName(), "created database");
    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(SQLiteHelper.class.getName(), "Upgrading database from version " + oldVersion + " to " + newVersion + ", which will destroy all old data");
        db.execSQL(SQL_DELETE_DATABASE);
        onCreate(db);
    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}