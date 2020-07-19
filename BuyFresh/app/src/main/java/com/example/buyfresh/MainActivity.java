package com.example.buyfresh;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {

    Button veggieButton ;
    String type;
    Button fruitButton;
    Button herbButton;
    private ImageView background;
    private ImageView farmOne;
    private TextView fruitText;
    private TextView herbText;
    private TextView veggieText;

    //Navigation Bar
    private DrawerLayout dl;
    private ActionBarDrawerToggle abdt;
    boolean darkMode = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // To change which activity appears first, search it on google, duh. This is the home page.

        // 12:24 finished XML, data collection, and design today. Tomorrow, lets add firebase, and Navigation bar.
        // 3:21 PM best way to fool-proof do that is in FirstLibrary and then copy and paste the code here.
        // 5:38 next day.. finished firebase, I have to go add the data today and set it up for this app

        dl = findViewById(R.id.dl);
        abdt = new ActionBarDrawerToggle(this, dl, R.string.Open, R.string.Close);
        abdt.setDrawerIndicatorEnabled(true);
        dl.addDrawerListener(abdt);
        abdt.syncState();
        veggieButton = findViewById(R.id.veggies);
        fruitButton = findViewById(R.id.fruits);
        herbButton = findViewById(R.id.herbs);
        fruitText = findViewById(R.id.fruitText);
        herbText = findViewById(R.id.herbsText);
        veggieText = findViewById(R.id.veggieText);

        farmOne = findViewById(R.id.farm_one);
        farmOne.setImageResource(R.drawable.farm_first);



        SharedPreferences sharedPreff = PreferenceManager.getDefaultSharedPreferences(this);
        // String
        final String dark_mode = sharedPreff.getString("dark_mode", "false");
        darkMode = Boolean.parseBoolean(dark_mode);

        if (darkMode){
            dl.setBackgroundColor(Color.parseColor("#2C2A2A"));
            veggieButton.setBackgroundResource(R.drawable.dark_square);
            fruitButton.setBackgroundResource(R.drawable.dark_square);
            herbButton.setBackgroundResource(R.drawable.dark_square);
            farmOne.setAlpha((float) 0.7);
            fruitText.setTextColor(Color.WHITE);
            herbText.setTextColor(Color.WHITE);
            veggieText.setTextColor(Color.WHITE);

        }else {

            dl.setBackgroundColor(Color.parseColor("#FFFFFF"));
            veggieButton.setBackgroundResource(R.drawable.white_square);
            fruitButton.setBackgroundResource(R.drawable.white_square);
            herbButton.setBackgroundResource(R.drawable.white_square);
            farmOne.setAlpha((float) 1);

            fruitText.setTextColor(Color.BLACK);
            herbText.setTextColor(Color.BLACK);
            veggieText.setTextColor(Color.BLACK);
        }

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final NavigationView nav_view = findViewById(R.id.nav_view);
        nav_view.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int id = menuItem.getItemId();
                if (id == R.id.profile) {
                    Intent car = new Intent(MainActivity.this, ProfileActivity.class);
                    startActivity(car);
                } else if (id == R.id.mSettings){
                    Intent car = new Intent(MainActivity.this, SettingsActivity.class);
                    startActivity(car);


                } else if (id == R.id.information) {
                    Intent car = new Intent(MainActivity.this, InformationActivity.class);
                    startActivity(car);

                } else if (id == R.id.home) {
                    Intent car = new Intent(MainActivity.this, MainActivity.class);
                    startActivity(car);
                }

                    return false;
            }
        });



        veggieButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent car = new Intent(MainActivity.this, FarmsListActivity.class);
                type = "VeggieFarmers";
                car.putExtra("type", type);
                startActivity(car);
                // Create object of SharedPreferences.
                SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
                //now get Editor
                SharedPreferences.Editor editor = sharedPref.edit();
                //put your value
                editor.putString("type", type);

                //commits your edits
                editor.commit();


            }
        });
        fruitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent car = new Intent(MainActivity.this, FarmsListActivity.class);
                type = "FruitFarmers";
                car.putExtra("type", type);
                startActivity(car);
                // Create object of SharedPreferences.
                SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
                //now get Editor
                SharedPreferences.Editor editor = sharedPref.edit();
                //put your value
                editor.putString("type", type);

                //commits your edits
                editor.commit();



            }
        });
        herbButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent car = new Intent(MainActivity.this, FarmsListActivity.class);
                type = "HerbFarmers";
                car.putExtra("type", type);
                startActivity(car);
                // Create object of SharedPreferences.
                SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
                //now get Editor
                SharedPreferences.Editor editor = sharedPref.edit();
                //put your value
                editor.putString("type", type);

                //commits your edits
                editor.apply();

            }
        });
        // Create object of SharedPreferences.
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
        //now get Editor
        SharedPreferences.Editor editor = sharedPref.edit();
        //put your value
        editor.putString("dark_mode", String.valueOf(darkMode));

        //commits your edits
        editor.apply();





    }

    //Nav

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return abdt.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);
    }
}