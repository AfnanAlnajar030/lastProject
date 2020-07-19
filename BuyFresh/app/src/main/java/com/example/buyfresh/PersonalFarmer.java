package com.example.buyfresh;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
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

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class PersonalFarmer extends AppCompatActivity {
    private Long phone ;
    private Button whatsApp;

    //Navigation Bar
    private DrawerLayout dl;
    private ActionBarDrawerToggle abdt;
    private ImageView background;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_farmer);

        dl = findViewById(R.id.dl);
        abdt = new ActionBarDrawerToggle(this, dl, R.string.Open, R.string.Close);
        abdt.setDrawerIndicatorEnabled(true);
        dl.addDrawerListener(abdt);
        abdt.syncState();
        background = findViewById(R.id.imageView2);
        SharedPreferences sharedPreff = PreferenceManager.getDefaultSharedPreferences(this);
        // String
        final String dark_mode = sharedPreff.getString("dark_mode", "false");
        final boolean darkMode = Boolean.parseBoolean(dark_mode);

        // DARK MODE
        if (darkMode){
            dl.setBackgroundColor(Color.parseColor("#2C2A2A"));
            background.setAlpha((float) 0.7);
        }else {

            dl.setBackgroundColor(Color.parseColor("#FFFFFF"));
            background.setAlpha((float) 1);
        }

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final NavigationView nav_view = findViewById(R.id.nav_view);
        nav_view.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int id = menuItem.getItemId();
                if (id == R.id.profile) {
                    Intent car = new Intent(PersonalFarmer.this, ProfileActivity.class);
                    startActivity(car);
                } else if (id == R.id.mSettings){
                    Intent car = new Intent(PersonalFarmer.this, SettingsActivity.class);
                    startActivity(car);


                } else if (id == R.id.information) {
                    Intent car = new Intent(PersonalFarmer.this, InformationActivity.class);
                    startActivity(car);

                } else if (id == R.id.home) {
                    Intent car = new Intent(PersonalFarmer.this, MainActivity.class);
                    startActivity(car);
                }

                return false;
            }
        });
        Bundle b = getIntent().getExtras();
        final String farmer = b.getString("which_farmer");
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        String type = sharedPref.getString("type", null);


        //ok so today I did a lot. I am pretty proud rn. Tomorrow, I have to set the data for each of the buttons and get them to click
        // I just realized.. when sending mName, will it give any name or just the one we clicked on?

        TextView name = findViewById(R.id.name);
        name.setText(farmer);

        ArrayList<Products> arrayList = new ArrayList<>();

        RecyclerView recyclerView = findViewById(R.id.recyclerview_farmers);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager lm = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(lm);

        Button moreBtn = findViewById(R.id.more);
        moreBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent car = new Intent(PersonalFarmer.this, moreActivity.class);
                car.putExtra("farmer_name", farmer);
                startActivity(car);

            }
        });
        whatsApp = findViewById(R.id.whatsapp);

        //setting up the phone number
        assert farmer != null;
        if (farmer.equals(getString(R.string.khrais))) {
            this.phone = Long.parseLong("96594470709");
        }  else if (farmer.equals(getString(R.string.abdaliGreens))) {
            this.phone = Long.parseLong("96598793089");
        } else if (farmer.equals(getString(R.string.badra))) {
            this.phone = Long.parseLong("96599244463");
        }else if (farmer.equals(getString(R.string.wafraCoop))) {
            this.phone = Long.parseLong("96550550539");
            whatsApp.setText(getString(R.string.doesntShip));
        } else if (farmer.equals(getString(R.string.ghanayim))) {
            this.phone = Long.parseLong("96566694142");
        } else if (farmer.equals(getString(R.string.khudrtna))) {
            this.phone = Long.parseLong("96550800837");
        } else if (farmer.equals(getString(R.string.basteen))) {
            this.phone = Long.parseLong("96599256574");
        }  else if (farmer.equals(getString(R.string.world))) {
            this.phone = Long.parseLong("96596615389");
        }  else if (farmer.equals(getString(R.string.nouf))) {
            this.phone = Long.parseLong("96598996345");
        } else {
            Toast.makeText(PersonalFarmer.this, "Oh no, phone number is wrong" + phone, Toast.LENGTH_LONG).show();
        }

        whatsApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //START HERE:
                String message = getString(R.string.message);

                PackageManager packageManager = getPackageManager();
                Intent i = new Intent(Intent.ACTION_VIEW);

                try {

                    String url = "https://api.whatsapp.com/send?phone="+ phone +"&text=" + URLEncoder.encode(message, "UTF-8");
                    i.setPackage("com.whatsapp");
                    i.setData(Uri.parse(url));
                    if (i.resolveActivity(packageManager) != null) {
                        startActivity(i);
                    }
                } catch (Exception e){
                    Toast.makeText(PersonalFarmer.this, getString(R.string.whatsAppGone), Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }

                //END HERE!

            }
        });





        //start
        assert type != null;
        if((type.equals(getString(R.string.FruitFarmers)) && name.getText().toString().equals(getString(R.string.khrais)))) {
            Products basket = new Products(getString(R.string.basket), "16 items", "27.5 kd");
            arrayList.add(basket);
        } else if (type.equals(getString((R.string.FruitFarmers))) &&  name.getText().toString().equals(getString(R.string.abdaliGreens))) {
            Products eggplant = new Products(getString(R.string.eggplant), "1 box", "1 kd");
            arrayList.add(eggplant);
            Products pumpkin = new Products(getString(R.string.pumpkin), "a big one", "3 kd");
            arrayList.add(pumpkin);
            Products greenPepper = new Products(getString(R.string.greenPepper), "1 box", "1.5 kd");
            arrayList.add(greenPepper);
            Products okra = new Products(getString(R.string.okra), "half a box", "1.25 kd");
            arrayList.add(okra);
            Products cucumber = new Products(getString(R.string.cucumber), "half a box", "0.5 kd");
            arrayList.add(cucumber);
            Products feildPumpkin = new Products(getString(R.string.feildPumpkin), "half a box", "1.25 kd");
            arrayList.add(feildPumpkin);

        } else if (type.equals(getString(R.string.FruitFarmers)) &&  name.getText().toString().equals(getString(R.string.badra))) {
            Products okra = new Products(getString(R.string.okra), "kilo", "3 kd");
            arrayList.add(okra);
            Products redPepper = new Products(getString(R.string.redPepper), "kilo", "3 kd");
            arrayList.add(redPepper);
            Products coldPepper = new Products(getString(R.string.coldPepper), "kilo", "1.5 kd");
            arrayList.add(coldPepper);
            Products cucumber = new Products(getString(R.string.cucumber), "half a box", "0.5 kd");
            arrayList.add(cucumber);
            Products eggplant = new Products(getString(R.string.eggplant), "1 box", "1 kd");
            arrayList.add(eggplant);
            Products smallEggplant = new Products(getString(R.string.eggplant), "small box", "5 kd");
            arrayList.add(smallEggplant);
            Products pumpkin = new Products(getString(R.string.pumpkinHoney), "box", "1 kd");
            arrayList.add(pumpkin);
        } else if (type.equals(getString(R.string.FruitFarmers)) &&  name.getText().toString().equals(getString(R.string.wafraCoop))) {
            Products fieldPumpkin = new Products(getString(R.string.feildPumpkin), "half a box", "0.295 kd");
            arrayList.add(fieldPumpkin);
            Products pumpkin = new Products(getString(R.string.pumpkinHoney), "half a box", "0.295 kd");
            arrayList.add(pumpkin);
            Products redPepper = new Products(getString(R.string.redPepper), "half a box", "0.295 kd");
            arrayList.add(redPepper);
            Products lemon = new Products(getString(R.string.lemon), "1/4 box", "0.295 kd");
            arrayList.add(lemon);
            Products cucumber = new Products(getString(R.string.cucumber), "a box", "0.195 kd");
            arrayList.add(cucumber);
            Products coldPepper = new Products(getString(R.string.coldPepper), "half a box", "0.295 kd");
            arrayList.add(coldPepper);
            Products apples = new Products(getString(R.string.apples), getString(R.string.kshf), "0.99 kd");
            arrayList.add(apples);
            Products pears = new Products(getString(R.string.pears), getString(R.string.kshf), "0.99 kd");
            arrayList.add(pears);
            Products peach = new Products(getString(R.string.peach), "packet", "0.55 kd");
            arrayList.add(peach);
            Products banana = new Products(getString(R.string.banana), "kilo", "0.33 kd");
            arrayList.add(banana);
            Products mshmsh = new Products(getString(R.string.mshmsh), getString(R.string.kshf), "0.99 kd");
            arrayList.add(mshmsh);
            Products grape = new Products(getString(R.string.grape), getString(R.string.kshf), "0.99 kd");
            arrayList.add(grape);
            Products cherry = new Products(getString(R.string.cherry), "packet", "0.77 kd");
            arrayList.add(cherry);
            Products watermelon = new Products(getString(R.string.watermelon), "kilo", "0.195 kd");
            arrayList.add(watermelon);
            Products afandy = new Products(getString(R.string.afandy), "packet", "0.55 kd");
            arrayList.add(afandy);

        }else if (type.equals(getString(R.string.FruitFarmers)) &&  name.getText().toString().equals(getString(R.string.ghanayim))){
            Products raman = new Products(getString(R.string.raman), "1 box", "2.5 kd");
            arrayList.add(raman);
            Products afandy = new Products(getString(R.string.afandy), "small box", "1.25 kd");
            arrayList.add(afandy);
            Products kiwi = new Products(getString(R.string.kiwi), "small box", "1.25 kd");
            arrayList.add(kiwi);
            Products okra = new Products(getString(R.string.okra), "1 box", "1 kd");
            arrayList.add(okra);
            Products tomato = new Products(getString(R.string.tomato), "small box", "1.5 kd");
            arrayList.add(tomato);
            Products watermelon = new Products(getString(R.string.watermelon), "kilo", "0.25 kd");
            arrayList.add(watermelon);
            Products cherry = new Products(getString(R.string.cherry), "dish", "0.75 kd");
            arrayList.add(cherry);
            Products eggplant = new Products(getString(R.string.eggplant), "1 box", "0.75 kd");
            arrayList.add(eggplant);
            Products pears = new Products(getString(R.string.pears), "kilo", "0.75 kd");
            arrayList.add(pears);
            Products apples = new Products(getString(R.string.apples), "kilo", "0.75 kd");
            arrayList.add(apples);
        }else if (type.equals(getString(R.string.FruitFarmers)) &&  name.getText().toString().equals(getString(R.string.khudrtna))) {
            Products okra = new Products(getString(R.string.okra), "1 box", "2 kd");
            arrayList.add(okra);
            Products eggplant = new Products(getString(R.string.eggplant), "1 box", "1.5 kd");
            arrayList.add(eggplant);
            Products feildPumpkin = new Products(getString(R.string.feildPumpkin), " a box", "2 kd");
            arrayList.add(feildPumpkin);
            Products pumpkin = new Products(getString(R.string.pumpkinHoney), "box", "1 kd");
            arrayList.add(pumpkin);
            Products tomato = new Products(getString(R.string.tomato), " box", "2 kd");
            arrayList.add(tomato);
            Products cucumber = new Products(getString(R.string.cucumber), "a box", "1 kd");
            arrayList.add(cucumber);
            Products watermelon = new Products(getString(R.string.watermelon), "one", "4 kd");
            arrayList.add(watermelon);
            Products lemon = new Products(getString(R.string.lemon), "small box", "1.25 kd");
            arrayList.add(lemon);
        } else if (type.equals(getString(R.string.FruitFarmers)) &&  name.getText().toString().equals(getString(R.string.basteen))) {
            Products apples = new Products(getString(R.string.apples), "basket", "2 kd");
            arrayList.add(apples);
            Products banana = new Products(getString(R.string.banana), "kilo", "1 kd");
            arrayList.add(banana);
            Products afandy = new Products(getString(R.string.afandy), "packet", "1.75 kd");
            arrayList.add(afandy);
            Products oranges = new Products(getString(R.string.oranges), "basket", "1.75 kd");
            arrayList.add(oranges);
            Products kiwi = new Products(getString(R.string.kiwi), "small box", "0.75 kd");
            arrayList.add(kiwi);
            Products strawberries = new Products(getString(R.string.straw), "small box", "0.75 kd");
            arrayList.add(strawberries);
            Products tomato = new Products(getString(R.string.tomato), " box", "1 kd");
            arrayList.add(tomato);
            Products grape = new Products(getString(R.string.grape), getString(R.string.kshf), "1 kd");
            arrayList.add(grape);

        } else if (type.equals(getString(R.string.FruitFarmers)) &&  name.getText().toString().equals(getString(R.string.world))) {
            Products basket = new Products(getString(R.string.group), getString(R.string.sonf) + "8", "14.5 kd");
            arrayList.add(basket);
            Products basket2 = new Products(getString(R.string.group), getString(R.string.sonf) + "18", "25 kd");
            arrayList.add(basket2);
            Products cherry = new Products(getString(R.string.cherry), "dish", "0.75 kd");
            arrayList.add(cherry);
            Products raman = new Products(getString(R.string.raman), "1 box", "3 kd");
            arrayList.add(raman);
            Products peach = new Products(getString(R.string.peach), "packet", "4 kd");
            arrayList.add(peach);

        } else if (type.equals(getString(R.string.FruitFarmers)) &&  name.getText().toString().equals(getString(R.string.nouf))) {
            Products apples = new Products(getString(R.string.apples), "kilo", "1 kd");
            arrayList.add(apples);
            Products afandy = new Products(getString(R.string.afandy), " kilo", "1 kd");
            arrayList.add(afandy);
            Products strawberries = new Products(getString(R.string.straw), "packet", "1 kd");
            arrayList.add(strawberries);
            Products raman = new Products(getString(R.string.raman), "kilo", "1 kd");
            arrayList.add(raman);
            Products coldPepper = new Products(getString(R.string.coldPepper), "small box", "1 kd");
            arrayList.add(coldPepper);
            Products okra = new Products(getString(R.string.okra), "small box", "1 kd");
            arrayList.add(okra);
            Products tomato = new Products(getString(R.string.tomato), "kilo", "1 kd");
            arrayList.add(tomato);
            Products lemon = new Products(getString(R.string.lemon), "small box", "1 kd");
            arrayList.add(lemon);
            Products pumpkin = new Products(getString(R.string.pumpkinHoney), "kilo", "1 kd");
            arrayList.add(pumpkin);
        } else if (type.equals(getString(R.string.HerbFarmers)) &&  name.getText().toString().equals(getString(R.string.khrais))) {
            Products basket = new Products(getString(R.string.basket), "16 items", "27.5 kd");
            arrayList.add(basket);
        } else if (type.equals(getString(R.string.HerbFarmers)) &&  name.getText().toString().equals(getString(R.string.abdaliGreens))){
            Products kazbara = new Products(getString(R.string.kazbara), "1 box", "3 kd");
            arrayList.add(kazbara);
            Products khas = new Products(getString(R.string.khas), getString(R.string.shda), "0.25 kd");
            arrayList.add(khas);
            Products bagdoonis = new Products(getString(R.string.bagdoonis), "1 box", "1.5 kd");
            arrayList.add(bagdoonis);
            Products shbnt = new Products(getString(R.string.shbnt), "1 box", "1.5 kd");
            arrayList.add(shbnt);
        }  else if (type.equals(getString(R.string.HerbFarmers)) &&  name.getText().toString().equals(getString(R.string.badra))){
            Products kazbara = new Products(getString(R.string.kazbara), getString(R.string.shda), "0.25 kd");
            arrayList.add(kazbara);
            Products bagdoonis = new Products(getString(R.string.bagdoonis), getString(R.string.shda), "0.25 kd");
            arrayList.add(bagdoonis);
            Products nana = new Products(getString(R.string.nana), getString(R.string.shda), "0.25 kd");
            arrayList.add(nana);
            Products jarjeer = new Products(getString(R.string.jarjeer), getString(R.string.shda), "0.4 kd");
            arrayList.add(jarjeer);
            Products bagl = new Products(getString(R.string.bagl), getString(R.string.shda), "0.25 kd");
            arrayList.add(bagl);
            Products barbeer = new Products(getString(R.string.barbeer), getString(R.string.shda), "0.25 kd");
            arrayList.add(barbeer);
            Products fajl = new Products(getString(R.string.fajl), getString(R.string.shda), "0.75 kd");
            arrayList.add(fajl);
            Products rayhan = new Products(getString(R.string.rayhan), getString(R.string.shda), "0.75 kd");
            arrayList.add(rayhan);

        } else if (type.equals(getString(R.string.HerbFarmers)) &&  name.getText().toString().equals(getString(R.string.khudrtna))) {
            Products nana = new Products(getString(R.string.nana), getString(R.string.shdaBig), "0.75 kd");
            arrayList.add(nana);
            Products bagdoonis = new Products(getString(R.string.bagdoonis), getString(R.string.shda), "0.75 kd");
            arrayList.add(bagdoonis);
            Products kazbara = new Products(getString(R.string.kazbara), getString(R.string.shda), "0.75 kd");
            arrayList.add(kazbara);
            Products shbnt = new Products(getString(R.string.shbnt), getString(R.string.shdaBig), "0.75 kd");
            arrayList.add(shbnt);
            Products jarjeer = new Products(getString(R.string.jarjeer), getString(R.string.shda), "0.75 kd");
            arrayList.add(jarjeer);
            Products barbeer = new Products(getString(R.string.barbeer), getString(R.string.shda), "0.75 kd");
            arrayList.add(barbeer);
            Products slk = new Products(getString(R.string.slk), getString(R.string.shda), "0.75 kd");
            arrayList.add(slk);
            Products bagl = new Products(getString(R.string.bagl), getString(R.string.shda), "0.75 kd");
            arrayList.add(bagl);
            Products rayhan = new Products(getString(R.string.rayhan), getString(R.string.shda), "0.75 kd");
            arrayList.add(rayhan);
            Products sabankh = new Products(getString(R.string.sabankh), getString(R.string.shda), "0.75 kd");
            arrayList.add(sabankh);
            Products roed = new Products(getString(R.string.roed), getString(R.string.shda), "0.75 kd");
            arrayList.add(roed);
            Products karfs = new Products(getString(R.string.karfs), getString(R.string.shda), "0.75 kd");
            arrayList.add(karfs);
            Products fajl = new Products(getString(R.string.fajl), getString(R.string.shda), "0.75 kd");
            arrayList.add(fajl);
            Products khas = new Products(getString(R.string.khas), getString(R.string.shda), "0.25 kd");
            arrayList.add(khas);
        } else if (type.equals(getString(R.string.HerbFarmers)) &&  name.getText().toString().equals(getString(R.string.basteen))) {
            Products bagdoonis = new Products(getString(R.string.bagdoonis), getString(R.string.shda), "0.25 kd");
            arrayList.add(bagdoonis);
            Products kari = new Products(getString(R.string.kari), getString(R.string.shda), "1 kd");
            arrayList.add(kari);
            Products fajl = new Products(getString(R.string.fajl), getString(R.string.shda), "0.75 kd");
            arrayList.add(fajl);
        } else if (type.equals(getString(R.string.HerbFarmers)) &&  name.getText().toString().equals(getString(R.string.ghanayim))) {
            Products khas = new Products(getString(R.string.khas), "1 ", "0.5 kd");
            arrayList.add(khas);
            Products shbnt = new Products(getString(R.string.shbnt), getString(R.string.shdaBig), "0.75 kd");
            arrayList.add(shbnt);
            Products kazbara = new Products(getString(R.string.kazbara), getString(R.string.shda), "0.75 kd");
            arrayList.add(kazbara);
        }  else if (type.equals(getString(R.string.VeggieFarmers)) &&  name.getText().toString().equals(getString(R.string.abdaliGreens))) {
            Products redOnion = new Products(getString(R.string.redOnion), "6 kilo ", "3 kd");
            arrayList.add(redOnion);
            Products onion = new Products(getString(R.string.Onion), "6 kilo ", "2.5 kd");
            arrayList.add(onion);
            Products broccoli = new Products(getString(R.string.broccoli), "1 box ", "1.5 kd");
            arrayList.add(broccoli);
            Products patato = new Products(getString(R.string.patato), "1 box ", "1.5 kd");
            arrayList.add(patato);
            Products malfoof = new Products(getString(R.string.malfoof), "1 box ", "1.٢5 kd");
            arrayList.add(malfoof);

        } else if (type.equals(getString(R.string.VeggieFarmers)) &&  name.getText().toString().equals(getString(R.string.badra))) {
            Products onion = new Products(getString(R.string.Onion), "4 kilo ", "2.5 kd");
            arrayList.add(onion);
            Products redOnion = new Products(getString(R.string.redOnion), "5 kilo ", "2 kd");
            arrayList.add(redOnion);
            Products mushroom = new Products(getString(R.string.mushroom), "6 kilo ", "3 kd");
            arrayList.add(mushroom);
            Products greenOnion = new Products(getString(R.string.greenOnion), "5 kilo ", "1 kd");
            arrayList.add(greenOnion);
            Products patato = new Products(getString(R.string.patato), "1 box ", "1.5 kd");
            arrayList.add(patato);

        } else if (type.equals(getString(R.string.VeggieFarmers)) &&  name.getText().toString().equals(getString(R.string.ghanayim))) {
            Products khas = new Products(getString(R.string.khas), "1 ", "0.75 kd");
            arrayList.add(khas);
            Products onion = new Products(getString(R.string.Onion), getString(R.string.shwal), "0.75 kd");
            arrayList.add(onion);
            Products kurkum = new Products(getString(R.string.kurkum), "350g", "0.75 kd");
            arrayList.add(kurkum);
            Products carrot = new Products(getString(R.string.carrot), "small box", "0.5 kd");
            arrayList.add(carrot);
            Products garlic = new Products(getString(R.string.garlic), "small box", "0.5 kd");
            arrayList.add(garlic);
            Products malfoofRed = new Products(getString(R.string.malfoofRed), "1 ", "0.5 kd");
            arrayList.add(malfoofRed);

        } else if (type.equals(getString(R.string.VeggieFarmers)) &&  name.getText().toString().equals(getString(R.string.world))){
            Products basket2 = new Products(getString(R.string.group), getString(R.string.sonf) + "18", "25 kd");
            arrayList.add(basket2);
        } else if (type.equals(getString(R.string.VeggieFarmers)) &&  name.getText().toString().equals(getString(R.string.khudrtna))) {
            Products onion = new Products(getString(R.string.Onion), "1 box", "2.5 kd");
            arrayList.add(onion);
            Products redOnion = new Products(getString(R.string.redOnion), "1 box", "2 kd");
            arrayList.add(redOnion);
            Products patato = new Products(getString(R.string.patato), "1 box ", "2 kd");
            arrayList.add(patato);
            Products garlic = new Products(getString(R.string.garlic), "small box", "1 kd");
            arrayList.add(garlic);
            Products carrot = new Products(getString(R.string.carrot), "1 bag", "1.5 kd");
            arrayList.add(carrot);
        } else if (type.equals(getString(R.string.VeggieFarmers)) &&  name.getText().toString().equals(getString(R.string.basteen))) {
            Products bagdoonis = new Products(getString(R.string.bagdoonis), "5" +  getString(R.string.rabdat), "0.25 kd");
            arrayList.add(bagdoonis);
            Products patato = new Products(getString(R.string.patato), "1 box ", "1 kd");
            arrayList.add(patato);
            Products onion = new Products(getString(R.string.Onion), "2 small boxes", "1 kd");
            arrayList.add(onion);
            Products broccoli = new Products(getString(R.string.broccoli), "1 box ", "1.5 kd");
            arrayList.add(broccoli);
            Products kary = new Products(getString(R.string.kari), "1 box ", ".5 kd");
            arrayList.add(kary);
            Products malfoof = new Products(getString(R.string.malfoof), "2 small boxes", "1 kd");
            arrayList.add(malfoof);
            Products malfoofRed = new Products(getString(R.string.malfoofRed), "2 small boxes ", "1 kd");
            arrayList.add(malfoofRed);
            Products garlic = new Products(getString(R.string.garlic), "2 small boxes", "1 kd");
            arrayList.add(garlic);
        }  else if (type.equals(getString(R.string.VeggieFarmers)) &&  name.getText().toString().equals(getString(R.string.nouf))) {
            Products fasoolya = new Products(getString(R.string.fasoolya), "2 small box2", "0.35 kd");
            arrayList.add(fasoolya);
            Products sabanikh = new Products(getString(R.string.sabankh), getString(R.string.rabda), "0.25 kd");
            arrayList.add(sabanikh);
            Products khas = new Products(getString(R.string.khas), "1 ", "0.25 kd");
            arrayList.add(khas);
            Products garlic = new Products(getString(R.string.garlic), "1 box", "0.35 kd");
            arrayList.add(garlic);
            Products patato = new Products(getString(R.string.patato), "kilo ", "0.25 kd");
            arrayList.add(patato);
            Products onion = new Products(getString(R.string.Onion), "kilo", "0.25 kd");
            arrayList.add(onion);
            Products Bigkhas = new Products(getString(R.string.khas), "1 big box ", "0.75 kd");
            arrayList.add(Bigkhas);
            Products malfoof = new Products(getString(R.string.malfoof), "kilo", "1 kd");
            arrayList.add(malfoof);
            Products carrot = new Products(getString(R.string.carrot), "1 small box", "1 kd");
            arrayList.add(carrot);
            Products corn = new Products(getString(R.string.corn), "1  box", "0.95 kd");
            arrayList.add(corn);
        } else if (type.equals(getString(R.string.VeggieFarmers)) &&  name.getText().toString().equals(getString(R.string.khrais))) {
            Products basket = new Products(getString(R.string.basket), "16 items", "27.5 kd");
            arrayList.add(basket);
        } else {
            Toast.makeText(PersonalFarmer.this, "Something went wrong : " + type +  name.getText().toString(), Toast.LENGTH_SHORT).show();
        }


            Adapter mAdapter = new Adapter(arrayList, this, darkMode);
        recyclerView.setAdapter(mAdapter);




    }
    //Nav

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return abdt.onOptionsItemSelected(item) || super.onOptionsItemSelected(item);
    }
}