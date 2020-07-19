package com.example.buyfresh;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

public class InformationActivityTwo extends AppCompatActivity {

    //Navigation Bar
    private DrawerLayout dl;
    private ActionBarDrawerToggle abdt;

    Button lastButton ;
    Button videoButton;
    private ImageView white;
    private ImageView background;
    private TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information_two);

        //Nav
        dl = findViewById(R.id.dl);
        abdt = new ActionBarDrawerToggle(this, dl, R.string.Open, R.string.Close);
        abdt.setDrawerIndicatorEnabled(true);
        dl.addDrawerListener(abdt);
        abdt.syncState();
        white = findViewById(R.id.white_box);
        textView = findViewById(R.id.text3);
        background = findViewById(R.id.imageView3);
        SharedPreferences sharedPreff = PreferenceManager.getDefaultSharedPreferences(this);
        // String
        final String dark_mode = sharedPreff.getString("dark_mode", "false");
        final boolean darkMode = Boolean.parseBoolean(dark_mode);

        // DARK MODE
        if (darkMode){
            dl.setBackgroundColor(Color.parseColor("#2C2A2A"));
            white.setImageResource(R.drawable.dark_square);
            background.setAlpha((float) 0.7);
            textView.setTextColor(Color.WHITE);
        }else {

            dl.setBackgroundColor(Color.parseColor("#FFFFFF"));
            white.setImageResource(R.drawable.white_square);
            background.setAlpha((float) 1);
            textView.setTextColor(Color.BLACK);
        }


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        final NavigationView nav_view = findViewById(R.id.nav_view);
        nav_view.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int id = menuItem.getItemId();
                if (id == R.id.profile) {
                    Intent car = new Intent(InformationActivityTwo.this, ProfileActivity.class);
                    startActivity(car);
                } else if (id == R.id.mSettings) {
                    Intent car = new Intent(InformationActivityTwo.this, SettingsActivity.class);
                    startActivity(car);


                } else if (id == R.id.information) {
                    Intent car = new Intent(InformationActivityTwo.this, InformationActivity.class);
                    startActivity(car);
                } else if (id == R.id.home) {
                    Intent car = new Intent(InformationActivityTwo.this, MainActivity.class);
                    startActivity(car);
                }

                return false;
            }
        });

        lastButton = findViewById(R.id.lastButton);
        videoButton = findViewById(R.id.vidBtn);

        lastButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent car = new Intent(InformationActivityTwo.this, InformationActivity.class );
                startActivity(car);
            }
        });

        videoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=Pcg38iZhibY")));
                Log.i("Video", "Video Playing....");
            }
        });


    }
    //Nav

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return abdt.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);
    }
}