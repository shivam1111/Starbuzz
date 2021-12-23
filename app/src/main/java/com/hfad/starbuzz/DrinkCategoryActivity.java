package com.hfad.starbuzz;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

public class DrinkCategoryActivity extends ListActivity {

    private SQLiteDatabase db;
    private Cursor cr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ListView listDrinks = getListView();
        //Code for Array Adapter
//        ArrayAdapter<Drink> listAdapter = new ArrayAdapter<Drink>(this,
//                android.R.layout.simple_list_item_1,
//                Drink.drinks);
//        listDrinks.setAdapter(listAdapter);

        // We will now use cursor adapter
        try{
            SQLiteOpenHelper starbuzzDatabaseOpenHelper = new StarbuzzDatabaseHelper(this);
            db = starbuzzDatabaseOpenHelper.getReadableDatabase();
            cr = db.query("DRINK",
                    new String[] {"_id","NAME"},null,null,null,null,null);
            CursorAdapter listAdapter = new SimpleCursorAdapter(this,
                    android.R.layout.simple_list_item_1,
                    cr,
                    new String[] {"NAME"},
                    new int[] {android.R.id.text1},
                    0);
            listDrinks.setAdapter(listAdapter);

        }catch (SQLiteException e) {
            Toast toast = Toast.makeText(this,"Database Unavailable",Toast.LENGTH_SHORT);
            toast.show();
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        cr.close();
        db.close();
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        Log.d("DrinkCategoryActivity","Position "+position+" ID "+id);
        Intent intent = new Intent(DrinkCategoryActivity.this,DrinkActivity.class);
        intent.putExtra(DrinkActivity.EXTRA_DRINKNO, (int) id);
        startActivity(intent);
    }
}