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

import com.bitvale.switcher.SwitcherX;
import com.google.android.material.navigation.NavigationView;

import java.util.Set;

public class SettingsActivity extends AppCompatActivity {


    private Button save ;
    private SwitcherX darkness ;
    private SwitcherX sound;
    boolean dark_mode = false;
    private ImageView white_box;
    private ImageView background;
    boolean music;
    MediaPlayer player;
    MediaPlayer ring;
    private TextView soundTxt;
    private TextView darkTxt;




    //Navigation Bar
    private DrawerLayout dl;
    private ActionBarDrawerToggle abdt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        dl = findViewById(R.id.dl);
        abdt = new ActionBarDrawerToggle(this, dl, R.string.Open, R.string.Close);
        abdt.setDrawerIndicatorEnabled(true);
        dl.addDrawerListener(abdt);
        abdt.syncState();


        SharedPreferences sharedPreff = PreferenceManager.getDefaultSharedPreferences(this);
        // String
        final String dark = sharedPreff.getString("dark_mode", "false");
        final boolean darkMode = Boolean.parseBoolean(dark);

        SharedPreferences sharedPrrf = PreferenceManager.getDefaultSharedPreferences(this);
        // String
        final String musica = sharedPreff.getString("music", "false");
        final boolean Music = Boolean.parseBoolean(musica);






        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final NavigationView nav_view = findViewById(R.id.nav_view);
        nav_view.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int id = menuItem.getItemId();
                if (id == R.id.profile) {
                    Intent car = new Intent(SettingsActivity.this, ProfileActivity.class);
                    startActivity(car);
                } else if (id == R.id.mSettings){
                    Intent car = new Intent(SettingsActivity.this, SettingsActivity.class);
                    startActivity(car);


                } else if (id == R.id.information) {
                    Intent car = new Intent(SettingsActivity.this, InformationActivity.class);
                    startActivity(car);

                } else if (id == R.id.home) {
                    Intent car = new Intent(SettingsActivity.this, MainActivity.class);
                    startActivity(car);
                }

                return false;
            }
        });

        // Ok, Activity's code starts here --- >

        save = findViewById(R.id.saveChanges);
        darkness = findViewById(R.id.dark_switch);
        sound = findViewById(R.id.switcher_sound);
        white_box = findViewById(R.id.white_box);
        background = findViewById(R.id.farm);
        soundTxt = findViewById(R.id.soundTxt);
        darkTxt = findViewById(R.id.darkTxt);

        if (darkMode) {
            darkness.setChecked(true, false);
            soundTxt.setTextColor(Color.WHITE);
            darkTxt.setTextColor(Color.WHITE);
        } else {
            darkness.setChecked(false, false);
            soundTxt.setTextColor(Color.BLACK);
            darkTxt.setTextColor(Color.BLACK);
        }
        if (ring!= null) {
            sound.setChecked(true, false);
        } else {
            sound.setChecked(false, false);
        }



        darkness.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                darkness.setChecked(!darkness.isChecked(), true);
                if (darkness.isChecked()) {
                    dark_mode = true;
                } else {
                    dark_mode = false;
                }
            }
        });
        if (darkMode) {
            dl.setBackgroundColor(Color.parseColor("#2C2A2A"));
            white_box.setImageResource(R.drawable.dark_square);
            background.setAlpha((float) 0.7);
            soundTxt.setTextColor(Color.WHITE);
            darkTxt.setTextColor(Color.WHITE);
        } else  {
            dl.setBackgroundColor(Color.parseColor("#FFFFFF"));
            white_box.setImageResource(R.drawable.white_square);
            background.setAlpha((float) 1);
            darkness.setChecked(false, false);
            soundTxt.setTextColor(Color.BLACK);
            darkTxt.setTextColor(Color.BLACK);
        }


        sound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sound.setChecked(!sound.isChecked(), true);
                // Here, we'll watch a video and see how it goes LOL!

                if(sound.isChecked()) {
                    ring = MediaPlayer.create(SettingsActivity.this, R.raw.music);
                    ring.isLooping();

                    music = true;
                } else {

                    music = false;

                }




            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dark_mode) {
                    dl.setBackgroundColor(Color.parseColor("#2C2A2A"));
                    white_box.setImageResource(R.drawable.dark_square);
                    background.setAlpha((float) 0.7);
                    soundTxt.setTextColor(Color.WHITE);
                    darkTxt.setTextColor(Color.WHITE);
                } else if (!dark_mode) {
                    dl.setBackgroundColor(Color.parseColor("#FFFFFF"));
                    white_box.setImageResource(R.drawable.white_square);
                    background.setAlpha((float) 1);
                    soundTxt.setTextColor(Color.BLACK);
                    darkTxt.setTextColor(Color.BLACK);
                }

                if (darkness.isChecked()){
                    dark_mode = true;

                } else {
                    dark_mode = false;
                }

                if (music) {


                    ring.start();

                } else {
                       if (ring != null ) {
                           ring.pause();

                           ring.stop();

                       }

                }


                // Create object of SharedPreferences.
                SharedPreferences sharedPrf = PreferenceManager.getDefaultSharedPreferences(SettingsActivity.this);
                //now get Editor
                SharedPreferences.Editor edditor = sharedPrf.edit();
                //put your value
                edditor.putString("music", String.valueOf(music));

                //commits your edits
                edditor.apply();

                // Create object of SharedPreferences.
                SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(SettingsActivity.this);
                //now get Editor
                SharedPreferences.Editor editor = sharedPref.edit();
                //put your value
                editor.putString("dark_mode", String.valueOf(dark_mode));

                //commits your edits
                editor.apply();

            }
        });





    }
    //Nav

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return abdt.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);
    }


}