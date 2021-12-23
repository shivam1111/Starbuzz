package com.hfad.starbuzz;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class DrinkActivity extends AppCompatActivity {

    public static final String EXTRA_DRINKNO = "drinkNo";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drink);

        //Get the drink from the intent
        int drinkNo = (Integer)getIntent().getExtras().get(EXTRA_DRINKNO);

        try{
            SQLiteOpenHelper starbuzzDatabaseHelper = new StarbuzzDatabaseHelper(this);
            SQLiteDatabase db = starbuzzDatabaseHelper.getReadableDatabase();
            Cursor cr = db.query("DRINK",
                    new String[] {"NAME","DESCRIPTION","IMAGE_RESOURCE_ID"},
                    "_id = ? ",
                    new String[] {Integer.toString(drinkNo)},
                    null,null,null);

            //Move to the first record
            if (cr.moveToFirst()){
                String nameText = cr.getString(0);
                String descriptionText = cr.getString(1);
                Integer photoId = cr.getInt(2);
                Log.d ("Database",nameText+" "+descriptionText);
                //Populate the drink name
                TextView name = (TextView) findViewById(R.id.name);
                name.setText(nameText);

                //Populate the drink image
                ImageView photo = (ImageView) findViewById(R.id.photo);
                photo.setImageResource(photoId);
                photo.setContentDescription(nameText);

                //Populate the drink description
                TextView description = (TextView) findViewById(R.id.description);
                description.setText(descriptionText);
            }
            cr.close();
            db.close();
        } catch (SQLException e){
            Toast toast = Toast.makeText(this,"Database Unavailable",Toast.LENGTH_SHORT);
            toast.show();
        }





    }

    public void onFavoriteCLicked(View view) {
    }
}