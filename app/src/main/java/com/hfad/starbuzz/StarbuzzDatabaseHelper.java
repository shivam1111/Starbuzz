package com.hfad.starbuzz;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class StarbuzzDatabaseHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "starbuzz";
    private  static final int DB_VERSION = 2;

    public StarbuzzDatabaseHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        updateMyDatabase(db,0,DB_VERSION);
    }

    private static void insertDrinks(SQLiteDatabase db, String name,String description,int resourceId){
        ContentValues drinkValue = new ContentValues();
        drinkValue.put("NAME",name);
        drinkValue.put("DESCRIPTION",description);
        drinkValue.put("IMAGE_RESOURCE_ID",resourceId);
        db.insert("DRINK",null,drinkValue);
    }

    private void updateMyDatabase(SQLiteDatabase db,int oldVersion,int newVersion){
        if (oldVersion < 1){
            db.execSQL("CREATE TABLE DRINK (" +
                    "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "NAME TEXT," +
                    "DESCRIPTION TEXT," +
                    "IMAGE_RESOURCE_ID INTEGER);");

            insertDrinks(db,"Latte","A couple of espresso shots with steamed milk", R.drawable.latte);
            insertDrinks(db,"Cappuccino", "Espresso, hot milk, and a steamed milk foam", R.drawable.cappuccino);
            insertDrinks(db,"Filter", "Highest quality beans roasted and brewed fresh", R.drawable.filter);
        }
        if (oldVersion < 2){
            db.execSQL("ALTER TABLE DRINK ADD COLUMN FAVORITE NUMERIC");
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        updateMyDatabase(db,0,DB_VERSION);
    }
}
