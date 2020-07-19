package com.example.buyfresh;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Base64;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

import java.util.HashMap;

public class ProfileActivity extends AppCompatActivity {


    //Navigation Bar
    private DrawerLayout dl;
    private ActionBarDrawerToggle abdt;

    private ImageView profileImage;
    private TextView nameS;
    private TextView emailTxt;
    private TextView delete;
    FirebaseAuth mAuth;
    private Button update;
    DatabaseReference mDatabase;
    private EditText password;
    private EditText passwordConfirm;
    private String pass ;
    private ImageView white;
    private ImageView background;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        // String
        final String user_name = sharedPref.getString("userName", "Not Available");
        final String names = user_name;

        SharedPreferences shared = PreferenceManager.getDefaultSharedPreferences(this);
        String path = shared.getString("imgure", "Not Available");

        SharedPreferences sharedPreff = PreferenceManager.getDefaultSharedPreferences(this);
        // String
        final String dark_mode = sharedPreff.getString("dark_mode", "false");
        final boolean darkMode = Boolean.parseBoolean(dark_mode);



        SharedPreferences sharedPre = PreferenceManager.getDefaultSharedPreferences(this);
        // String
        String email = sharedPre.getString("email", "Not Available");


        mDatabase = FirebaseDatabase.getInstance().getReference("Users");


        nameS = findViewById(R.id.nameees);
        emailTxt = findViewById(R.id.email_input);
        delete = findViewById(R.id.delete);
        mAuth = FirebaseAuth.getInstance();
        update = findViewById(R.id.updateProfile);
        password = findViewById(R.id.password);
        passwordConfirm = findViewById(R.id.passwordTwo);
        white = findViewById(R.id.white_box);
        background = findViewById(R.id.farm_three);

        // Navigation
        dl = findViewById(R.id.dl);
        abdt = new ActionBarDrawerToggle(this, dl, R.string.Open, R.string.Close);
        abdt.setDrawerIndicatorEnabled(true);
        dl.addDrawerListener(abdt);
        abdt.syncState();

        // DARK MODE
        if (darkMode){
            dl.setBackgroundColor(Color.parseColor("#2C2A2A"));
            white.setImageResource(R.drawable.dark_square);
            background.setAlpha((float) 0.7);
            emailTxt.setTextColor(Color.parseColor("#FFFFFF"));
        }else {

            dl.setBackgroundColor(Color.parseColor("#FFFFFF"));
            white.setImageResource(R.drawable.white_square);
            background.setAlpha((float) 1);
        }

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseUser user = mAuth.getCurrentUser();
                if (user != null) {
                    user = null;
                    Intent car =  new Intent(ProfileActivity.this, SignInActivity.class);
                    startActivity(car);
                }
            }
        });





        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!isEmpty()) {
                    HashMap hashMap = new HashMap();
                    hashMap.put("username", pass);
                    pass = password.getText().toString();
                    String passTwo = passwordConfirm.getText().toString();


                    mDatabase.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).updateChildren(hashMap).addOnSuccessListener(new OnSuccessListener() {
                        @Override
                        public void onSuccess(Object o) {
                            Toast.makeText(ProfileActivity.this, "Username updated successfully", Toast.LENGTH_SHORT).show();
                            nameS.setText(pass);

                        }
                    });
                }

            }
        });

        if (pass == null) {
            nameS.setText(user_name);

        } else {
            nameS.setText(pass);
        }

        emailTxt.setText(email);



        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final NavigationView nav_view = findViewById(R.id.nav_view);
        nav_view.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int id = menuItem.getItemId();
                if (id == R.id.profile) {
                    Intent car = new Intent(ProfileActivity.this, ProfileActivity.class);
                    startActivity(car);
                } else if (id == R.id.mSettings){
                    Intent car = new Intent(ProfileActivity.this, SettingsActivity.class);
                    startActivity(car);


                } else if (id == R.id.information) {
                    Intent car = new Intent(ProfileActivity.this, InformationActivity.class);
                    startActivity(car);

                } else if (id == R.id.home) {
                    Intent car = new Intent(ProfileActivity.this, MainActivity.class);
                    startActivity(car);
                }

                return false;
            }
        });

    }


    //Nav

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return abdt.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);
    }
    private boolean isEmpty(){

        if(TextUtils.isEmpty (password.getText ().toString ())){
            password.setError ("REQUIRED");
            return true;
        }
        if(TextUtils.isEmpty (passwordConfirm.getText ().toString ())){
            passwordConfirm.setError ("REQUIRED");
            return true;
        }

        if(!password.getText().toString().equals(passwordConfirm.getText().toString())) {
            passwordConfirm.setError("PASSWORD DOESN'T MATCH");
            return true;
        }
        return false;
    }


}