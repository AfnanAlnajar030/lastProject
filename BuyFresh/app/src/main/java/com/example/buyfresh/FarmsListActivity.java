package com.example.buyfresh;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import java.io.Serializable;
import java.util.List;

public class FarmsListActivity extends AppCompatActivity{

    private RecyclerView mRecyclerView;
    private String type;
    private ImageView background;


    //Navigation Bar
    private DrawerLayout dl;
    private ActionBarDrawerToggle abdt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_farms_list);

        dl = findViewById(R.id.dl);
        abdt = new ActionBarDrawerToggle(this, dl, R.string.Open, R.string.Close);
        abdt.setDrawerIndicatorEnabled(true);
        dl.addDrawerListener(abdt);
        abdt.syncState();
        background = findViewById(R.id.farm_two);

        SharedPreferences sharedPreff = PreferenceManager.getDefaultSharedPreferences(this);
        // String
        final String dark_mode = sharedPreff.getString("dark_mode", "false");
        final boolean darkMode = Boolean.parseBoolean(dark_mode);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final NavigationView nav_view = findViewById(R.id.nav_view);
        nav_view.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int id = menuItem.getItemId();
                if (id == R.id.profile) {
                    Intent car = new Intent(FarmsListActivity.this, ProfileActivity.class);
                    startActivity(car);
                } else if (id == R.id.mSettings){
                    Intent car = new Intent(FarmsListActivity.this, SettingsActivity.class);
                    startActivity(car);

                } else if (id == R.id.information) {
                    Intent car = new Intent(FarmsListActivity.this, InformationActivity.class);
                    startActivity(car);
                } else if (id == R.id.home) {
                    Intent car = new Intent(FarmsListActivity.this, MainActivity.class);
                    startActivity(car);
                }

                return false;
            }
        });
        // DARK MODE
        if (darkMode){
            dl.setBackgroundColor(Color.parseColor("#2C2A2A"));
            background.setAlpha((float) 0.7);
        } else {
            dl.setBackgroundColor(Color.parseColor("#FFFFFF"));
            background.setAlpha((float) 1);
        }
        Bundle b = getIntent().getExtras();
          type = b.getString("type");

        mRecyclerView =  findViewById(R.id.recyclerview_farmers);

        new FirebaseDatabaseHelper(type).readBooks(new FirebaseDatabaseHelper.DataStatus() {
            @Override
            public void DataIsLoaded(List<Farmer> books, List<String> keys) {
                findViewById(R.id.progress).setVisibility(View.GONE);
                new RecyclerView_Config().setConfig(mRecyclerView, FarmsListActivity.this, books, keys, darkMode);
            }

            @Override
            public void DataIsInserted() {

            }

            @Override
            public void DataIsIsUpdated() {

            }

            @Override
            public void DataIsDeleted() {

            }
        });

    }
    //Nav

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return abdt.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);
    }


}